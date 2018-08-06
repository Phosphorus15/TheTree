A generic solution for plenty of markup language
----
##### Supported Languages:
 - [x] Json (parser / serializer)
 - [ ] Xml/Html (parser / serializer)
 - [ ] Yaml (parser / serializer)
 - [x] Arbre (parser / serializer)
 
Extensibility are expected due to the hierarchical tree of node type, which is with high flexibility
 
Currently, it is compatible with data types including :
 - int8 ~ int64
 - big integer and decimal
 - common string value
 - comments | annotations
 - generic list (without type restriction)
 - imitative map (not yes finished)
 
##### What's arbre:
 arbre is a systematic way of storing a tree structure, originated form French "L'arbre" (means "The tree").
 
 arbre would be stored in a binary file type named ".lar", or get embedded in a Blob stream. commonly it would be 
 compressed using gzip, nevertheless, raw arbre is also acceptable under certain circumstances. The file structure
 of lar is somewhat elastic and is a good way to store the content of a markup language file with less worry of being modified.
 
 It's also a good idea to use it as an implicit database.