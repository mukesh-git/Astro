# Astro
Smaple app
This application is guide to channels for the direct tv services provided by Astro.There are 2 screens :
The home screen shows list of channels with channel ids. One can sort by channel id and channel name. Also, one can mark channel as favourites.There is a Gmail login functionality using Firebase Authentication. Login is mandatory to mark favourites. Once user is logged in, the Favourites and Sorting preferences are backed up on Firebase Realtime Database.Whenever user logs in again the Favourites and Sort order is preserved.
The second screen has the list of tv shows on air currently from all channels. One can sort by channel number and name. This screen is pagination and lazy load enabled, allowing the user to see upto next 7 days of shows.
