import "lastfm:lastfm.ql"
import "spotify:playlist_tools.ql"

provider "lastfm"

LastFmResult result = getRecentTracks("RubbaBoy")

if (result.isError()) {
    printf("Error: %s", [result.errorMessage])
    exit(1)
}

print("RubbaBoy's top played songs this month:")

for (track : result.wrappedData.getValue()) {
    print(track)
//    printf("%s\t plays  %s", [track.playCount, track.track])
}

print("Wrapped:")
print(result.attr)

//provider "spotify"
//
//collection myPlaylist = createPlaylist("Last.Fm Top Songs")
//
//addToPlaylist(myPlaylist, result.data)
//print("Created a playlist with %s songs".format([result.data.size()]))
