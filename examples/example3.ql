import "lastfm:lastfm.ql"
import "spotify:playlist_tools.ql"

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

collection newPlaylist = createPlaylist("Top Song Playlist")
addToPlaylist(newPlaylist, result.data)

print("Created a playlist with %s songs".format([result.data.size()]))
