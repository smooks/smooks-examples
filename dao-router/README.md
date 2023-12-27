About
=====

This example illustrates fragment-driven database look-ups and persistence thanks to the [Smooks Persistence Cartridge](https://github.com/smooks/smooks-persistence-cartridge). Each Smooks config in the project is independent of the other and offers the same functionality but highlights a different entity persistence framework:

#### [`smooks-dao-config.xml`](smooks-configs/smooks-dao-config.xml) 

Configures Smooks to use Data Access Objects for accessing the database; [Hibernate](https://hibernate.org/orm/) is the persistence provider.

#### [`smooks-jpa-config.xml`](smooks-configs/smooks-jpa-config.xml)

Configures Smooks to use entities for accessing the database; Hibernate is the persistence provider.

#### [`smooks-mybatis-config.xml`](smooks-configs/smooks-mybatis-config.xml) 

Configures Smooks to use Data Access Objects for accessing the database; [MyBatis](https://mybatis.org/mybatis-3/) is the persistence provider.

### How to run?

1. `mvn clean package`
2. `mvn exec:exec`
3. Follow the command prompts