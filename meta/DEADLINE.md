# Deadline

Modify this file to satisfy a submission requirement related to the project
deadline. Please keep this file organized using Markdown. If you click on
this file in your GitHub repository website, then you will see that the
Markdown is transformed into nice looking HTML.

## Part 1: App Description

> Please provide a firendly description of your app, including the
> the primary functions available to users of the app. Be sure to
> describe exactly what APIs you are using and how they are connected
> in a meaningful way.

> **Also, include the GitHub `https` URL to your repository.**

The users will enter a country name. I change this into a country code.
Using this code, I request information from http://api.worldbank.org/v2/country/br?format=json (example using brazil)
World Bank:  https://datahelpdesk.worldbank.org/knowledgebase/articles/898590-country-api-queries
This gives me the country name, capital city name, and the longitude and latitude values for the capital city.
I use the latitude and longitude values to search for the sunrise and sunset times from
https://sunrise-sunset.org/api . An example query would be: https://api.sunrise-sunset.org/json?lat=36.7201600&lng=-4.4203400
I display the country name, capital city name, and the sunrise and sunset times for the capital city.
My GitHub URL: https://github.com/nehaa0504/cs1302-api

## Part 2: New

> What is something new and/or exciting that you learned from working
> on this project?

Learning about accessing APIs was incredibly educational. I participated
in UGAHacks earlier this year and was completely clueless as to what API even meant.
I feel like my experience with this project will really help me in my future hackathons.

## Part 3: Retrospect

> If you could start the project over from scratch, what do
> you think might do differently and why?

I might have spent more time learning how to make the display look prettier. Or, I may
have incorporated more information from each website. I was also interested in creating multiple
scene graphs with buttons linking to a different scene graph to display completely different things.
