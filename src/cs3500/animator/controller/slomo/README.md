# SloMo File Format
For use with excellence SloMo filename should match the name of the readable but have "SloMo"
at then end.

i.e. "bogoSort3.txt"'s slow file would be "bogoSort3SloMo.txt"

## Each Line
Should be a space-separated line as follows:

{frame rate} + " " + {start frame} + " " + {end frame}

i.e ("30 20 100")

## Restrictions

- Frame rate cannot be lower than 1
- Start frame cannot be lower than 1
- End frame cannot be lower than 1
- Start frame cannot be after end frame.
- No two slow motion events can occur on the same frame.
  - The exception is one SloMo's end frame can be another's start frame.
- Frame cannot be outside the animation's maximum frame.