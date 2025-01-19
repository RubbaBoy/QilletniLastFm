import "lastfm:lastfm.ql"
import "spotify:playlist_tools.ql"
import "spotify:play_redirect.ql"

provider "lastfm"

Page page = new Page()
                ..page = 1
                ..count = 100

LastFmResult result = getTopTracks("RubbaBoy", "3month", page)

if (result.isError()) {
    printf("Error: %s", [result.errorMessage])
    exit(1)
}

for (track : result.wrappedData.getValue()) {
    printf("%s\t plays  %s", [track.playCount, track.track])
}

provider "spotify"

song[] spotifyTracks = result.data

song[] top20  = spotifyTracks.subList(0, 20)
song[] next30 = spotifyTracks.subList(20, 50)
song[] last50 = spotifyTracks.subList(50, 100)

weights topWeighted =
    | 50% top20
    | 30% next30
    | 20% last50

weights chooseTop =
    | 50% topWeighted

collection metalMusic = "My Playlist #59" collection by "rubbaboy" weights[chooseTop]

// Add 100 songs to a new playlist

song[] songList = []
redirectPlayToList(songList)

play metalMusic limit[100]

collection newPlaylist = createPlaylist("Top Song Weighted Playlist")
addToPlaylist(newPlaylist, songList)

print("Created a playlist with %s songs".format([songList.size()]))
