# Advent of Code 2022
I'm learning Java/Spring Boot atm so I decided to make a web app for my solutions this year.

When running you can request a list of puzzles from: `localhost:8080/api/puzzles`  
and individual puzzle results from: `localhost:8080/api/puzzles/{year}/{day}`  
It will attempt to pull down the puzzle input from `https://adventofcode.com/{year}/day/{day}/input`. This requires a cookie to get your personal input, which is not uploaded but can be created by copying your information from the developer tools once you are logged onto the site and placing them in a `cookie.txt` file in the root of the repo.
