
# Minecraft Cheat Client Base 💣


Small Minecraft Cheat Client Base for your project 🕹

## Basic information

The client is built on Maven, all external libs will be added by Maven 📚

Libs that are not included in Maven and need to be imported manually in your project:
- **openauth** - for Microsoft Login
- **ViaVersion** - Lib for ViaVersion
- **ViaBackwards** - Support for ViaVersion
- **ViaRewind** - Support for ViaVersion
- **ViaSnakeYaml** - Support for ViaVersion

#### Required
- Java 11+
- Maven

#### How to Run Client
- Open project in your IDE
- Add all external libs what is not included in Maven
- Run MainClass (Start.java)

#### Overview of Client
I made this little base client out of boredom and because someone asked me to, if you need help with some basic module just use my project and change it or use it, nice if you add in your client that you used my project if you will do that. When creating this base, I also used various mods created by GitHub users 💖

Prefix of Commands: **#**

You can change prefix in **CommandManager.java**:
```
public static final String COMMAND_PREFIX = "#";
```

I have created example commands and modules that will help you understand how this system works. **Remember that if you create a new command or module, you must implement it to the list that is in ClientBase.java**

```
CommandManager.getManager().addCommands(
        new AuthorCommand(),
        new HelpCommand()
);

CrashManager.getManager().addMethod(
        new TestMethod(),
        new Test2Method()
);
```

I've also created some basic visual information and extras that can help you create your own gui!

#### Overview of Commands
- **#author** - Check Client Author and his SocialMedia
- **#help** - Check all commands and they description
- **#crash** - Check/Send Crash Methods to the Server

Crash Command Example:
```
#crash <name> <packets>
#crash test 100
```

#### Client Suppot
If you need help with the client, text to me:
- Discord: 0WhiteDev#0001
- Email: 0whitedev@gmail.com
## Authors

- [@0WhiteDev](https://github.com/0WhiteDev)

