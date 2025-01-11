
/*
 * User functions
 */

native fun getFriends(user)

native fun getFriends(user, page)

native fun getUser(user)

native fun getLovedTracks(user)

// TODO: get tags

native fun getRecentTracks(user)

native fun getTopAlbums(user)

native fun getTopArtists(user)

native fun getTopTracks(user)

native fun getWeeklyAlbumChart(user)

native fun getWeeklyArtistChart(user)

native fun getWeeklyChartList(user)

native fun getWeeklyTrackChart(user)

/*
 * Chart functions
 */

native fun getTopArtists()
native fun getTopTracks()
//native fun getTopTags()

entity Page {
    /**
     * The limit of items per page.
     */
    int count = 50
    
    /**
     * The page index.
     */
    int page = 0
    
    Page()
}
