About
=====

This example illustrates fragment-driven database look-ups and persistence thanks to the [Smooks Persistence Cartridge](https://github.com/smooks/smooks-persistence-cartridge). Each Smooks config in the project is independent of the other and offers the same functionality but highlights a different entity persistence framework:

* [`smooks-dao-config.xml`](smooks-configs/smooks-dao-config.xml): Smooks uses Data Access Objects to access the database. Hibernate is the persistence provider.
* [`smooks-jpa-config.xml`](smooks-configs/smooks-jpa-config.xml): Smooks uses entities to access the database. Hibernate is the persistence provider.
* [`smooks-mybatis-config.xml`](smooks-configs/smooks-mybatis-config.xml): Smooks uses Data Access Objects to access the database. MyBatis is the persistence provider.

### How to run?

1. `mvn clean package`
2. `mvn exec:exec`
3. Follow the terminal prompts