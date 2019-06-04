package xin.xiaoa.englishlearn.article2words;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;



public class Article2Words {
    //保存文章的内容
    String content;
    //保存分割后的单词集合
    String[] rawWords;
    //保存统计后的单词集合
    String[] words;
    //保存单词对应的词频
    int[] wordFreqs;
    private List<A2wListviewItem> a2wLVLists;

    //构造函数，输入文章内容
    public Article2Words(String content) {
        this.content = content;
//        this.content  = "kolya is one of the richest films i've seen in some time." +
//                "zdenek sverak plays a confirmed old bachelor ( who's likely to" +
//                " remain so ) , who finds his life as a czech cellist increasingly " +
//                "impacted by the five-year old boy that he's taking care of . though " +
//                "it ends rather abruptly-- and i'm whining , 'cause i wanted to spend " +
//                "more time with these characters-- the acting , writing , and production " +
//                "values are as high as , if not higher than , comparable american dramas. " +
//                "this father-and-son delight-- sverak also wrote the script , while his son , " +
//                "jan , directed-- won a golden globe for best foreign language film and , " +
//                "a couple days after i saw it , walked away an oscar . in czech and russian , " +
//                "with english subtitles . ";
    }

    //对文章根据分隔符进行分词,将结果保存到rawWords数组中
    public void splitWord(){
//分词的时候，因为标点符号不参与，所以所有的符号全部替换为空格
        final char SPACE = ' ';
        content = content.replace('\"', SPACE)
                .replace(',', SPACE)
                .replace('.', SPACE)
                .replace('(', SPACE)
                .replace(')', SPACE)
                .replace('-', SPACE)
                .replace('-', SPACE)
                .replace('“', SPACE)
                .replace('”', SPACE);

        rawWords = content.split("\\s+");//凡是空格隔开的都算单词，上面替换了', 所以I've 被分成2个 //单词
    }
    //统计词，遍历数组
    public void countWordFreq() {
//将所有出现的字符串放入唯一的set中，不用map,是因为map寻找效率太低了
        Set<String> set = new TreeSet<String>();
        for(String word: rawWords){
            set.add(word);
        }
        Iterator ite = set.iterator();
        List<String> wordsList = new ArrayList<String>();
        List<Integer> freqList = new ArrayList<Integer>();
//多少个字符串未知，所以用list来保存先
        while(ite.hasNext()){
            String word = (String) ite.next();
            int count = 0;//统计相同字符串的个数
            for(String str: rawWords){
                if(str.equals(word)){
                    count++;
                }
            }
            wordsList.add(word);
            freqList.add(count++);
        }
//存入数组当中
        words = wordsList.toArray(new String[0]);
        wordFreqs = new int[freqList.size()];
        for(int i = 0; i < freqList.size(); i++){
            wordFreqs[i] = freqList.get(i);
        }
    }
    //根据词频，将词数组和词频数组进行降序排序
    public void sort() {
        List wordList = new ArrayList<Word>();
        for(int i = 0; i < words.length; i++){
            wordList.add(new Word(words[i], wordFreqs[i]));
        }
        Collections.sort(wordList, new WordComparator());
        for(int i = 0; i < wordList.size(); i++){
            Word wor = (Word) wordList.get(i);
            words[i] = wor.word;
            wordFreqs[i] = wor.freq;
        }
    }
    class Word{
        private String word;
        private int freq;
        public Word(String word, int freq){
            this.word = word;
            this.freq = freq;
        }
    }
    //注意：此处排序，1）首先按照词频降序排列， 2）如果词频相同，按照字母降序排列，
//如 'abc' > 'ab' >'aa'
    class WordComparator implements Comparator {
        public int compare(Object o1, Object o2) {
            Word word1 = (Word) o1;
            Word word2 = (Word) o2;
            if(word1.freq < word2.freq){
                return 1;
            }else if(word1.freq > word2.freq){
                return -1;
            }else{
                return 0;
//                int len1 = word1.word.trim().length();
//                int len2 = word2.word.trim().length();
//                String min = len1 > len2? word2.word: word1.word;
//                String max = len1 > len2? word1.word: word2.word;
//                for(int i = 0; i < min.length(); i++){
//                    if(min.charAt(i) < max.charAt(i)){
//                        return 1;
//                    }
//                }
//                return 1;
            }
        }
    }
    //将排序结果输出
    public List<A2wListviewItem> getLists() {
        a2wLVLists = new ArrayList<>();
        for(int i = 0; i < words.length; i++){
            a2wLVLists.add(new A2wListviewItem(words[i],wordFreqs[i]));
            System.out.println(words[i]+" - "+wordFreqs[i]);
        }
        return a2wLVLists;
    }
//    //测试类的功能
//    public static void main(String[] args) {
//        Article a = new Article();
//        a.splitWord();
//        a.countWordFreq();
//        a.sort();
//        a.printResult();
//    }


}
