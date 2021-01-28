### Written Answers: 6/6

### Code Quality: 3/3

### Mechanics: 3/3

### General Feedback
Some minor issues, but overall good work

### Specific Feedback
- When selecting a greeting in `RandomHello` it's better to use the array length to specify the maximum value. This allows for flexibility when adding/removing greetings in the future.
- Make sure to remove useless comments, like TODOs and commented out code, to make your code as clear as possible.
- Your add and remove methods are more complicated than they need to be. Take a look at `Set.add` and `Set.remove` and see if they handle things that you've written
- Ball comparison is better written with `Double.compare`. Take a look at the documentation for it and see if it satisfies your needs.
