## UML



### UML Class Diagram

- **Definition** ：The [UML](https://en.wikipedia.org/wiki/Unified_Modeling_Language) Class diagram is a graphical notation used to construct and visualize object oriented systems. A class diagram in the Unified Modeling Language (UML) is a type of static structure diagram that describes the structure of a system by showing the system's:

  - classes
  - their attributes
  - operations
  - the relations among objects

- Example

  - How to understand a class? 

    - A Class is a blueprint for an object. Objects and classes go hand in hand. We can't talk about one without talking about the other. And the entire point of Object-Oriented Design is not about objects, it's about classes, because we use classes to create objects. So a class describes what an object will be, but it isn't the object itself.

      In fact, classes describe the type of objects, while objects are usable instances of classes. Each Object was built from the same set of blueprints and therefore contains the same components (properties and methods). The standard meaning is that an object is an instance of a class and object - Objects have states and behaviors.

  - Take dog as a Example

    ```
    class Dog
    {
    	private:
    		// properties
    		Color
    		Eye Color
    		Height
    		Weight
    		//methods
    		sit()
    		Lay_Down()
    		Shake()
    		Come()
    }
    ```

- **UML Class Notation** 

  - How to write a UML Class diagram

    - Class Name:

      - The name of the class appears in the first partition.

    - Class Attributes

      - Attributes are shown in the second partition.
      - The attribute type is shown after the colon(:).
      - Attributes map onto member variables (data members) in code.

    - Class Operations(Methods):

      - Operations are shown in the third partition. They are services the class provides.
      - The return type of a method is shown after the colon at the end of the method signature.
      - The return type of method parameters are shown after the colon following the parameter name. Operations map onto class methods in code

    - Class Visibility

      - +:  public attributes or operations
      - -:   private attributes or operations
      - #:  protected attributes or operations

    - Parameter Directionality

      - Each parameter in an operation (method) may be denoted as in, **out** or **inout** which specifies its direction with respect to the caller. This directionality is shown before the parameter name.

    - Relationships between classes

      - Inheritance(or Generalization)

        - A generalization is a taxonomic relationship between a more general classifier and a more specific classifier. Each instance of the specific classifier is also an indirect instance of the general classifier. Thus, the specific classifier inherits the features of the more general classifier.
        - ![09-inheritance-hierarchy-example](C:\Users\15052\Desktop\博客\09-inheritance-hierarchy-example.webp)

      - Association

        - Associations are relationships between classes in a UML Class Diagram. They are represented by a solid line between classes. Associations are typically named using a verb or verb phrase which reflects the real world problem domain.

      - Aggregation

        - It represents a "part of" relationship.

      - Composition

        - A special type of aggregation where parts are destroyed when the whole is destroyed.

      - Dependency

        - An object of one class might use an object of another class in the code of a method. If the object is not stored in any field, then this is modeled as a dependency relationship.

      - Realization

        - Realization is a relationship between the blueprint class and the object containing its respective implementation level details. This object is said to realize the blueprint class. In other words, you can understand this as the relationship between the interface and the implementing class.

        

  