import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinder;
import opennlp.tools.namefind.TokenNameFinderEvaluator;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;
import opennlp.tools.util.eval.FMeasure;

public class EvaluateNERModels {

    public String Tokens[];

    public static void main(String[] args) throws IOException
             {
    	//relevant=list of all relevant things
    	//retrieved=results of a query
        //precision=TP/(TP+FP)
        //recall=TP/(TP+FN)
    	//f-measure=2(precision x recall)/(precision + recall)
    	int a;
    	int b;
		String [] datasets = {"evaluation_data/twitter_data/","twitter_merged.txt","evaluation_data/enron_data/","enron_merged.txt"};

		//merge all text files in datasets
    	List twitter_file_paths=Util.listTextFiles(datasets[0]);
/*      	for(int i=0; i < twitter_file_paths.size();i++ )
    	{
        	Util.writeFile(Util.readFile(twitter_file_paths.get(i).toString()) + "\n", datasets[0]+datasets[1], true);
        	System.out.println("Merging file: "+twitter_file_paths.get(i).toString());
    	} 
    	
		
    	List enron_file_paths=Util.listAllFiles(datasets[2]);
      	for(int i=0; i < enron_file_paths.size();i++ )
    	{
        	Util.writeFile(Util.readFile(enron_file_paths.get(i).toString()) + "\n", datasets[2]+datasets[3], true);
        	System.out.println("Merging file: "+enron_file_paths.get(i).toString());
    	}*/
    	
      	
      	try{
      	
        EvaluateNERModels toi = new EvaluateNERModels();
		Charset charset = Charset.forName("UTF-8");
		
		for(int i=0; i<2;i++){
			if(i<1){
				a=0;
				b=1;				
			}
			else{
				a=2;
				b=3;
			}
//		ObjectStream<String> lineStream_person = new PlainTextByLineStream(new FileInputStream(datasets[a]+datasets[b]), charset);
		ObjectStream<String> lineStream_person = new PlainTextByLineStream(new FileInputStream("evaluation_data/reuters_data/reuters_entities.txt"), charset);
		ObjectStream<NameSample> sampleStream_person = new NameSampleDataStream(lineStream_person);
        InputStream modelIn_person = new FileInputStream("models/ner/en-ner-person.bin");
		TokenNameFinderModel model_person = new TokenNameFinderModel(modelIn_person);
		TokenNameFinderEvaluator evaluator_person = new TokenNameFinderEvaluator(new NameFinderME(model_person));
		evaluator_person.evaluate(sampleStream_person);
		FMeasure result_person = evaluator_person.getFMeasure();
		System.out.println("Person entity evaluation for "+ datasets[b]+": \n"+result_person.toString()+"\n");
		lineStream_person.close();
		sampleStream_person.close();
		modelIn_person.close();
		
        InputStream modelIn_loc = new FileInputStream("models/ner/en-ner-location.bin");
		TokenNameFinderModel model_loc = new TokenNameFinderModel(modelIn_loc);
		TokenNameFinderEvaluator evaluator_loc = new TokenNameFinderEvaluator(new NameFinderME(model_loc));
//		ObjectStream<String> lineStream_loc = new PlainTextByLineStream(new FileInputStream(datasets[a]+datasets[b]), "UTF-8");
		ObjectStream<String> lineStream_loc = new PlainTextByLineStream(new FileInputStream("evaluation_data/reuters_data/reuters_entities.txt"), charset);
		ObjectStream<NameSample> sampleStream_loc = new NameSampleDataStream(lineStream_loc);	
		
		evaluator_loc.evaluate(sampleStream_loc);
		FMeasure result_loc = evaluator_loc.getFMeasure();
		System.out.println("Location entity evaluation for "+ datasets[b]+": \n"+result_loc.toString()+"\n");
		lineStream_loc.close();
		sampleStream_loc.close();
		modelIn_loc.close();
		}
      	
      	
		
		/*ObjectStream<String> lineStream_time = new PlainTextByLineStream(new FileInputStream("evaluation_data/twitter_entities_time.txt"), charset);
		ObjectStream<NameSample> sampleStream_time = new NameSampleDataStream(lineStream_time);
        InputStream modelIn_time = new FileInputStream("models/ner/en-ner-time.bin");
		TokenNameFinderModel model_time = new TokenNameFinderModel(modelIn_time);
		TokenNameFinderEvaluator evaluator_time = new TokenNameFinderEvaluator(new NameFinderME(model_time));
		evaluator_time.evaluate(sampleStream_time);
		FMeasure result_time = evaluator_time.getFMeasure();
		System.out.println("Time entity evaluation: \n"+result_time.toString()+"\n");
        
		ObjectStream<String> lineStream_date = new PlainTextByLineStream(new FileInputStream("evaluation_data/twitter_entities_date.txt"), charset);
		ObjectStream<NameSample> sampleStream_date = new NameSampleDataStream(lineStream_date);
        InputStream modelIn_date = new FileInputStream("models/ner/en-ner-date.bin");
		TokenNameFinderModel model_date = new TokenNameFinderModel(modelIn_date);
		TokenNameFinderEvaluator evaluator_date = new TokenNameFinderEvaluator(new NameFinderME(model_date));
		evaluator_date.evaluate(sampleStream_date);
		FMeasure result_date = evaluator_date.getFMeasure();
		System.out.println("Date entity evaluation: \n"+result_date.toString()+"\n");
		
		ObjectStream<String> lineStream_org = new PlainTextByLineStream(new FileInputStream("evaluation_data/twitter_entities_organization.txt"), charset);
		ObjectStream<NameSample> sampleStream_org = new NameSampleDataStream(lineStream_org);
        InputStream modelIn_org = new FileInputStream("models/ner/en-ner-organization.bin");
		TokenNameFinderModel model_org = new TokenNameFinderModel(modelIn_org);
		TokenNameFinderEvaluator evaluator_org = new TokenNameFinderEvaluator(new NameFinderME(model_org));
		evaluator_org.evaluate(sampleStream_org);
		FMeasure result_org = evaluator_org.getFMeasure();
		System.out.println("Date entity evaluation: \n"+result_org.toString()+"\n");

        
		ObjectStream<String> lineStream_money = new PlainTextByLineStream(new FileInputStream("evaluation_data/twitter_entities_money.txt"), charset);
		ObjectStream<NameSample> sampleStream_money = new NameSampleDataStream(lineStream_money);
        InputStream modelIn_money = new FileInputStream("models/ner/en-ner-money.bin");
		TokenNameFinderModel model_money = new TokenNameFinderModel(modelIn_money);
		TokenNameFinderEvaluator evaluator_money = new TokenNameFinderEvaluator(new NameFinderME(model_money));
		evaluator_money.evaluate(sampleStream_money);
		FMeasure result_money = evaluator_money.getFMeasure();
		System.out.println("Money entity evaluation: \n"+result_money.toString()+"\n");
        
		ObjectStream<String> lineStream_percent = new PlainTextByLineStream(new FileInputStream("evaluation_data/twitter_entities_percentage.txt"), charset);
		ObjectStream<NameSample> sampleStream_percent = new NameSampleDataStream(lineStream_percent);
        InputStream modelIn_percent = new FileInputStream("models/ner/en-ner-percentage.bin");
		TokenNameFinderModel model_percent = new TokenNameFinderModel(modelIn_percent);
		TokenNameFinderEvaluator evaluator_percent = new TokenNameFinderEvaluator(new NameFinderME(model_percent));
		evaluator_percent.evaluate(sampleStream_percent);
		FMeasure result_percent = evaluator_percent.getFMeasure();
		System.out.println("Percentage entity evaluation: \n"+result_percent.toString()+"\n");*/
        
		
		
//		String input = Util.readFile("evaluation_data/twitter_data/test2.test");	
		String input="I am going to <START:location> Philadelphia <END>."+" \n \n"+
				"I am going to <START:location> New York <END>.";
                
        toi.tokenization(input);
        String names = toi.namefind(toi.Tokens);
        String org = toi.orgfind(toi.Tokens);
        String loc = toi.locfind(toi.Tokens);
        String time = toi.timefind(toi.Tokens);
        String date = toi.datefind(toi.Tokens);
        String money = toi.moneyfind(toi.Tokens);
        String percentage = toi.percentagefind(toi.Tokens);


        System.out.print("person name is: "+names);
        System.out.println("person name is: "+names.length());
        System.out.println("person count is: "+toi.nameCount(toi.Tokens));

        System.out.print("organization name is: "+org);
        System.out.println("organization name is: "+org.length());

        System.out.print("location name is: "+loc);

        System.out.print("time name is: "+time);
        System.out.println("time name is: "+time.length());

        System.out.print("date name is: "+date);
        System.out.println("date name is: "+date.length());

        System.out.print("money name is: "+money);
        System.out.println("money name is: "+money.length());

        System.out.print("percentage name is: "+percentage);
        System.out.println("percentage name is: "+percentage.length());
        
             }

 	   catch (FileNotFoundException e) {
		   System.out.println("No File");		   
		   }
/*	   catch (IOException e) {
		   System.out.println("IOException");		   
		   }*/
        


    }
    public int nameCount(String cnt[]) {
        InputStream is;
        TokenNameFinderModel tnf;
        NameFinderME nf;
        String sd = "";
        try {
            is = new FileInputStream(
                    "models/ner/en-ner-person.bin");
            tnf = new TokenNameFinderModel(is);
            nf = new NameFinderME(tnf);

            Span sp[] = nf.find(cnt);

            String a[] = Span.spansToStrings(sp, cnt);
            StringBuilder fd = new StringBuilder();
            int l = a.length;
            is.close();

            return l;


        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (InvalidFormatException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return 0;
    }

    public String percentagefind(String cnt[]) {
        InputStream is;
        TokenNameFinderModel tnf;
        NameFinderME nf;
        String sd = "";
        try {
            is = new FileInputStream(
                    "models/ner/en-ner-percentage.bin");
            tnf = new TokenNameFinderModel(is);
            nf = new NameFinderME(tnf);

            Span sp[] = nf.find(cnt);

            String a[] = Span.spansToStrings(sp, cnt);
            StringBuilder fd = new StringBuilder();
            int l = a.length;

            for (int j = 0; j < l; j++) {
                fd = fd.append(a[j] + "\n");

            }
            sd = fd.toString();
            is.close();


        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (InvalidFormatException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return sd;
    }
        
    
    
    public String moneyfind(String cnt[]) {
        InputStream is;
        TokenNameFinderModel tnf;
        NameFinderME nf;
        String sd = "";
        try {
            is = new FileInputStream(
                    "models/ner/en-ner-money.bin");
            tnf = new TokenNameFinderModel(is);
            nf = new NameFinderME(tnf);

            Span sp[] = nf.find(cnt);

            String a[] = Span.spansToStrings(sp, cnt);
            StringBuilder fd = new StringBuilder();
            int l = a.length;

            for (int j = 0; j < l; j++) {
                fd = fd.append(a[j] + "\n");

            }
            sd = fd.toString();
            is.close();


        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (InvalidFormatException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return sd;
    }
        
    
    
    public String datefind(String cnt[]) {
    InputStream is;
    TokenNameFinderModel tnf;
    NameFinderME nf;
    String sd = "";
    try {
        is = new FileInputStream(
                "models/ner/en-ner-date.bin");
        tnf = new TokenNameFinderModel(is);
        nf = new NameFinderME(tnf);

        Span sp[] = nf.find(cnt);

        String a[] = Span.spansToStrings(sp, cnt);
        StringBuilder fd = new StringBuilder();
        int l = a.length;

        for (int j = 0; j < l; j++) {
            fd = fd.append(a[j] + "\n");

        }
        sd = fd.toString();
        is.close();

        

    } catch (FileNotFoundException e) {

        e.printStackTrace();
    } catch (InvalidFormatException e) {

        e.printStackTrace();
    } catch (IOException e) {

        e.printStackTrace();
    }
    return sd;
}
    
    
    	public String namefind(String cnt[]) {
        InputStream is;
        TokenNameFinderModel tnf;
        NameFinderME nf;
        String sd = "";
        try {
            is = new FileInputStream(
                    "models/ner/en-ner-person.bin");
            tnf = new TokenNameFinderModel(is);
            nf = new NameFinderME(tnf);

            Span sp[] = nf.find(cnt);

            String a[] = Span.spansToStrings(sp, cnt);
            StringBuilder fd = new StringBuilder();
            int l = a.length;

            for (int j = 0; j < l; j++) {
                fd = fd.append(a[j] + "\n");

            }
            sd = fd.toString();
            is.close();


        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (InvalidFormatException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return sd;
    }

    public String orgfind(String cnt[]) {
        InputStream is;
        TokenNameFinderModel tnf;
        NameFinderME nf;
        String sd = "";
        try {
            is = new FileInputStream(
                    "models/ner/en-ner-organization.bin");
            tnf = new TokenNameFinderModel(is);
            nf = new NameFinderME(tnf);
            Span sp[] = nf.find(cnt);
            String a[] = Span.spansToStrings(sp, cnt);
            StringBuilder fd = new StringBuilder();
            int l = a.length;

            for (int j = 0; j < l; j++) {
                fd = fd.append(a[j] + "\n");

            }

            sd = fd.toString();
            is.close();


        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (InvalidFormatException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return sd;

    }
    
    
    public String locfind(String cnt[]) throws IOException {
        InputStream is;
        TokenNameFinderModel tnf;
        NameFinderME nf;
        String sd = "";
        try {
            is = new FileInputStream(
                    "models/ner/en-ner-location.bin");
            tnf = new TokenNameFinderModel(is);
            nf = new NameFinderME(tnf);
            Span sp[] = nf.find(cnt);
            String a[] = Span.spansToStrings(sp, cnt);
            StringBuilder fd = new StringBuilder();
            int l = a.length;

            for (int j = 0; j < l; j++) {
                fd = fd.append(a[j] + "\n");

            }

            sd = fd.toString();
            is.close();


        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (InvalidFormatException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        return sd;

    }

    
    
    public String timefind(String cnt[]) {
        InputStream is;
        TokenNameFinderModel tnf;
        NameFinderME nf;
        String sd = "";
        try {
            is = new FileInputStream(
                    "models/ner/en-ner-time.bin");
            tnf = new TokenNameFinderModel(is);
            nf = new NameFinderME(tnf);
            Span sp[] = nf.find(cnt);
            String a[] = Span.spansToStrings(sp, cnt);
            StringBuilder fd = new StringBuilder();
            int l = a.length;

            for (int j = 0; j < l; j++) {
                fd = fd.append(a[j] + "\n");

            }

            sd = fd.toString();
            is.close();


        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (InvalidFormatException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        
        return sd;

    }


    public void tokenization(String tokens) {

        InputStream is;
        TokenizerModel tm;

        try {
            is = new FileInputStream("models/en-token.bin");
            tm = new TokenizerModel(is);
            Tokenizer tz = new TokenizerME(tm);
            Tokens = tz.tokenize(tokens);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}