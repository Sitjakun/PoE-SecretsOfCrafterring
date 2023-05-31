import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Songs {

    private Map<String, String> rawSongs;
    private final Map<String, List<Integer>> songs;

    public Songs() {
        instanciateRawSongs();
        songs = new HashMap<>();
        analyzeSongs();
    }

    private void instanciateRawSongs() {
        rawSongs = new HashMap<>();
        rawSongs.put(SongsName.BLACKSMITH_ARM.name(), "sqcqdqdqddqdqdqdd ccq sddscszcssqsscz z z  zcz z zssccz qsssczqsssscqsqq"); //drum
        rawSongs.put(SongsName.DEAR_MR_HARRISON.name(), "czszsqzsqsd dsqssdsqd dszsdsq qdqszsdsqzs dsdsqsqzzzdsqzqdzqqqsd csd sqsd szdsqsqzzzsd dsd dsqzss dszqsdsqzdsqzqdsqzqd dsdsqsqzzzsc");
        rawSongs.put(SongsName.EMBARK.name(), "cdsqs szqsd qsdsd  qdsqzcssqqdscsddsz dszsdq dd dqsd cqcsscsscdcdcscscsdqsscsscqsdcsscqsccdzscsscsscsdc");
        rawSongs.put(SongsName.HONEY_N_BISCUITS.name(), "dszqssd szqszqsd  sqszqqsssd qszqsdqsdsqzsqqcssds sdd sszqsdqsddd dqqszzsqsdcss dssqqzzqsszqsd sqzsdzqsd sqsdzqsd dqszsdzzsqsdsdszqsc"); // mandolin
        rawSongs.put(SongsName.HOW_ORE_YOU_TODAY.name(), "ssdzqsdssqzsscss dzqssddssqqs sszz qszcddsqqs dds qqdd  sszzd sc");
        rawSongs.put(SongsName.IRONWOOD_LEAVES.name(), "");
        rawSongs.put(SongsName.MORNING_CHORES.name(), "");
        rawSongs.put(SongsName.POTTERS_SPRINT.name(), "");
        rawSongs.put(SongsName.SNEAKY_THE_SMUGGLER.name(), "");
        rawSongs.put(SongsName.SOUP_OF_THE_DAY.name(), "");
        rawSongs.put(SongsName.THE_BUTTERFLY.name(), "");
        rawSongs.put(SongsName.WEAVERS_WEBB.name(), "");
        rawSongs.put(SongsName.WINDSWARD_INN.name(), "");
        rawSongs.put(SongsName.WYRDWOOD_LEAVES.name(), "zzzcqs sqsdqss dqsdszsqsqdsdqdsdqdsddsd dsd zdsdcdsdc");
    }

    private void analyzeSongs() {
        rawSongs.forEach((name, song) -> songs.put(name, parseSong(song)));
    }

    private List<Integer> parseSong(String rawSong) {
        char[] chars = rawSong.toCharArray();
        List<Integer> song = new ArrayList<>();
        for (char c : chars) {
            switch (c) {
                case 'z':
                    song.add(KeyEvent.VK_Z);
                    break;
                case 'q':
                    song.add(KeyEvent.VK_Q);
                    break;
                case 's':
                    song.add(KeyEvent.VK_S);
                    break;
                case 'd':
                    song.add(KeyEvent.VK_D);
                    break;
                case ' ':
                    song.add(KeyEvent.VK_SPACE);
                    break;
                case 'c':
                    song.add(KeyEvent.VK_C);
                    break;
            }
        }
        return song;
    }

    public Map<String, List<Integer>> getSongs() {
        return songs;
    }
}
