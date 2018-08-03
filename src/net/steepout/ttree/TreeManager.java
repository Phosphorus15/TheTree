package net.steepout.ttree;

import net.steepout.ttree.parser.TreeParser;
import net.steepout.ttree.parser.TreeProcessor;
import net.steepout.ttree.parser.TreeSerializer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class TreeManager {

    private static Map<String, TreeParser> parsers = new HashMap<>();

    private static Map<String, TreeSerializer> serializers = new HashMap<>();

    private static Map<String, String> aliases = new TreeMap<>();

    public static final int VERSION = 0x110; // [primary][secondary][build]

    public static void registerAliases(String name, String... alias) {
        Arrays.stream(alias).filter(Objects::nonNull).map(String::toLowerCase)
                .forEach(s -> aliases.put(s, name.toLowerCase()));
    }

    /**
     * @param parser - the parser to be registered
     * @return whether a former parser has been replaced
     */
    public static boolean registerParser(TreeParser parser) {
        return parsers.put(parser.getName().toLowerCase(), parser) != null;
    }

    /**
     * @param serializer - the serializer to be registered
     * @return whether a former serializer has been replaced
     */
    public static boolean registerSerializer(TreeSerializer serializer) {
        return serializers.put(serializer.getName().toLowerCase(), serializer) != null;
    }

    public static boolean registerProcessor(TreeProcessor processor) {
        boolean result = false;
        result = registerSerializer(processor);
        result |= registerParser(processor);
        return result;
    }

    public static TreeSerializer findSerializer(String name) {
        name = name.toLowerCase();
        if (serializers.containsKey(name)) return serializers.get(name);
        else if (aliases.containsKey(name) && serializers.containsKey(aliases.get(name)))
            return serializers.get(aliases.get(name));
        else return null;
    }

    public static TreeParser findParser(String name) {
        name = name.toLowerCase();
        if (parsers.containsKey(name)) return parsers.get(name);
        else if (aliases.containsKey(name) && parsers.containsKey(aliases.get(name)))
            return parsers.get(aliases.get(name));
        else return null;
    }

    public static Set<String> availableProcessors() {
        Set<String> set = new HashSet<>();
        set.addAll(parsers.keySet());
        set.addAll(serializers.keySet());
        return set;
    }

    public static TreeSerializer defaultSerializer() {
        return findSerializer("arbre");
    }

    public static TreeParser defaultParser() {
        return findParser("arbre");
    }

    public static <T extends EditableNode> T emptyNodeByClass(Class<T> tClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        try {
            return tClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            Optional<Constructor<?>> optional = Arrays.stream(tClass.getConstructors())
                    .filter(constructor -> constructor.getParameterCount() == 1).findAny();
            if (optional.isPresent()) {
                return (T) optional.get().newInstance(new Object[]{null});
            } else throw new RuntimeException("Could not find applicable constructor for " + tClass.getName());
        }
    }

}
