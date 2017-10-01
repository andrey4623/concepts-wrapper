# Concepts wrapper

This module wraps an input text in the following way:
- Entities should be wrapped in "strong" tags;
- Links should be wrapped in "a href" tags that point to the corresponding links;
- Twitter usernames should be wrapped in "a href" tags that point to
"http://twitter.com/username" and are displayed as the username.

## Example

```java
class MyClass {
  public static void main(String[] args){
    String source = "Alex visited Facebook headquarters: http://bit.ly/xyz @andrey4623";
    
    List<Concept> concepts = new ArrayList<>();
    concepts.add(new Entity(new Concept.Border(13, 21)));
    concepts.add(new Entity(new Concept.Border(0, 4)));
    concepts.add(new Twitter(new Concept.Border(54, 65)));
    concepts.add(new Link(new Concept.Border(36, 53)));
    
    String result = Wrapper.wrap(source, concepts);
    System.out.println(result);
  }
}
```

The output is 

```html
<strong>Alex</strong> visited <strong>Facebook</strong> headquarters: <a href=”http://bit.ly/xyz”>http://bit.ly/xyz</a> @ <a href=”http://twitter.com/andrey4623”>andrey4623</a>
```

## Adding new concepts

Adding new concepts is easy: create a new class and extend it from Concept. You need to add a constructor and override getFormatString() method. 

```java
public class Hashtag extends Concept {

  public Hashtag(Border border) {
    super(ConceptType.HASHTAG, border);
  }

  @Override
  protected String getFormatString() {
    return "#%1$s";
  }
}
```

If you want to use multiple borders:
```java
public class Image extends Concept {

  public Image(Border imgUrl, Border alt) {
    super(ConceptType.IMAGE, Arrays.asList(imgUrl, alt));
  }

  @Override
  protected String getFormatString() {
    return "<img src=\"%1$s\" alt=\"%2$s\">";
  }
}
```

If you want to add some preprocessing logic:
```java
@Override
  protected String[] preprocess(String[] source) {
    source[0] = source[0].substring(1);
    return source;
  }
```

## Requirements

- Java 1.8 or newer

## Building

The project requires Java 1.8 and Maven 3.3.9.

```sh
$ mvn clean install
```

## License

MIT
