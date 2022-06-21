[< Back](../README.md)

# Environments :four_leaf_clover:

Below is the table of the current project environments and their respective configuration files in key-value format, note that the settings in the "[application.properties](../src/main/resources/application.properties)" file are global and will always be applied or overwritten by the environment(s) currently active:
|Environment           |ID   |Configurations file                                                           |Activation                                         |
|----------------------|:---:|:----------------------------------------------------------------------------:|---------------------------------------------------|
|-                     |-    |[application.properties](../src/main/resources/application.properties)           |global configurations applyed to all environments  |
|Development (default) |dev  |[application-dev.properties](../src/main/resources/application-dev.properties)   |manual                                             |
|Production            |prod |[application-prod.properties](../src/main/resources/application-prod.properties) |manual                                             |
|Integration tests     |it   |[application-it.properties](../src/test/resources/application-it.properties)     |automatic (during integration tests execution)     |
|Unit tests            |ut   |[application-ut.properties](../src/test/resources/application-ut.properties)     |automatic (during unit tests execution)            |

The environment activated by default is the **"Development"**, this is configured in the **"spring.profiles.active"** property of the "[application.properties](../src/main/resources/application.properties)" file.
To change the activated environment, you must create a system environment variable called **"RSHOP_ACTIVE"**, informing the ID of the environment to be activated. Many configuration properties of each environment have default values that can be substituted using environment variables, the names of the variables can be found in the configuration files themselves or in this list: [System environment variables](../docs/SYS_ENV_VARIABLES.md). <br />

During the execution of automated tests, the **Unit tests** or **Integration tests** environments are automatically activated depending on the type of test to be executed. Global settings are also applied or overitten in this case.
