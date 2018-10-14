# AnimationEngine
Java Animation/Game Engine that runs simple 2D games in *multiple* environments.

### Installing

* Clone the repository with the 'git clone' command for now. (may turn this into a maven repo at some point)

## Features

Write your 2d Game (engine and pipeline) ONCE, but deploy on:
 * Android
 * PC(Swing and JavaFX)

## Usage

Simply have one class extend any of the available engine's(depending on what functionality you require) and another extend the Pipeline(or use the default one).
Then start it with either the Swing or the JavaFX starter or set the AnimationView as ContentView on Android.
For a more detailed explanation:
* See the java doc commentary on the api itself.
* (If I didn't come around to writing that commentary yet see the EvadeGame or AGameOfLife implementation repo's)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details