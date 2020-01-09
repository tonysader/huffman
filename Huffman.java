package huffman;
import java.util.Scanner;
class NodeType{
    char symbol;
    int freq;
    int father;
    boolean isleft;
    boolean notUsed;
         
}
class CodeType{
    char bits[] = new char[500];
    int startPos;
}
public class Huffman {
    public static int findMinFreq(int p,NodeType[] nodes){
        int min = 99;
        int l=-1;
        for(int i=0; i<p;i++){
            if(nodes[i].freq < min && nodes[i].notUsed){
                min = nodes[i].freq;
                l = i;
            }
        }
        nodes[l].notUsed = false;
        return l;
    }
    public static int getFreqOfChar(char c, char[] sen){
        int re = 0;
        for(int i= 0 ; i<sen.length;i++){
            if(sen[i] == c)
                re ++;
        }
        return re;
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        char symbol;
        NodeType nodes[] = new NodeType[500];
        
        System.out.println("Enter the sentence you want to encode");
        char[] sentence = in.next().toCharArray();
        int n = sentence.length;
        for(int i=0 ; i <((2*n)-1 );i++)
            nodes[i] = new NodeType();
        for(int i=0 ; i <n ;i++){
            symbol = sentence[i];
            nodes[i].freq = getFreqOfChar(symbol, sentence);
            nodes[i].symbol = symbol;
            nodes[i].notUsed = true;
        }
        int p;
        for(p=n;p<((2*n) -1);p++){
            int p1 = findMinFreq(p, nodes);
            int p2 = findMinFreq(p,nodes);
            nodes[p1].isleft = true;
            nodes[p2].isleft = false;
            nodes[p1].father = p;
            nodes[p2].father =p;
            nodes[p].freq = nodes[p1].freq + nodes[p2].freq;
            nodes[p].notUsed = false;
        }
        
        int root = p-1;
        CodeType[] codes = new CodeType[500];
        CodeType code;
        for(int i=0; i < n ; i++){
            code = new CodeType();
            code.startPos = 500;
            p = i;
            while(p != root)
            {
                code.startPos--;
                if(nodes[p].isleft)
                    code.bits[code.startPos] = '0';
                else
                    code.bits[code.startPos] = '1';
                p = nodes[p].father;
            }
            codes[i] = code;
        }
        
        
        for(int i=0;i<n ; i++){
            System.out.println(nodes[i].symbol + ": freq: " + nodes[i].freq + " :" );
            for(int j=codes[i].startPos; j <500;j++)
                System.out.print(codes[i].bits[j]);
        System.out.println();
            
        }
        for(int i=0;i<sentence.length;i++){
            System.out.print(getCodeOfChar(sentence[i],codes,nodes,n));
        }
        System.out.println();
    }
    public static String getCodeOfChar(char c, CodeType[] codes,NodeType[] nodes,int n){
        String re = "";
        for(int i=0;i<n;i++){
            if(nodes[i].symbol == c)
            {
                String subRe = "";
                for(int j=codes[i].startPos;j<500;j++){
                    subRe += Character.toString(codes[i].bits[j]);
                }
                re += subRe;
                break;
            }    
        }
        return re;
    }
    
}
