README for CS3500 HW7

4-16-21

## Model
We tried to make as few changes as possible to our model. The only major change was switching over
all functionality to run on pre-rendering. We also removed some methods that were no longer
necessary to the running of our animation.

## Views
The only change to our previous three views was to the simple visual view, which now runs on
pre-rendering. We designed our interactive view to be as intuitive as possible, opting to use
a slider for adjusting the framerate rather than a button.

## Controller 
We added an interactive controller, which runs off the subscriber pattern. It waits for inputs
sent from the interactive view to trigger changes, rather than running off of a readable input.