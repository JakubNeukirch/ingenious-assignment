# Github Users

## Project structure

The project uses multi-module approach. As the project is fairly simple it only implements layer-modules for proper
hermetization of the code. However, in the future if the project and team grows, it can be divided also into
feature-modules. Each feature-module would contain layer modules.
The modules currently implemented:

- `app` - the so call "dirty-main", it connects all the modules into working application. This is the place where
  navigation and dependency injection is defined.
- `ui` - the `view` layer, this is the place for all widgets and pages. This separation assures easy UI-framework
  migration in future, it would require just replacing this module with another.
- `presentation` - the `presentation` layer, here you place all the `ViewModel`s
- `data` - the `data` layer, here you place all the `Repository`s, which connect with the `DataSource`s to implement the
  business logic.
- `datasource` - the `datasource` layer, here you place all the `DataSource`s which contain lower-level code to fetch
  data from the network or database.

## Dependencies

The gradle dependencies are defined in `/gradle/libs.versions.toml` file for consistency across modules.
