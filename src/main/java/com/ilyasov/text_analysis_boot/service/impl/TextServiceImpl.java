package com.ilyasov.text_analysis_boot.service.impl;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.ilyasov.text_analysis_boot.service.TextService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.AttributeSource;
import org.springframework.stereotype.Service;

/**
 * @author Damir Ilyasov
 */
@Service
public class TextServiceImpl implements TextService {
  Analyzer analyzer = new RussianAnalyzer();

  @Override
  public List<String> convert(StringBuilder text) {
    List<String> result = new ArrayList<>();
    try {
      TokenStream stream = analyzer.tokenStream("contents", new StringReader(text.toString()));
      stream.reset();
      while (stream.incrementToken()) {
        AttributeSource token = stream.cloneAttributes();
        CharTermAttribute termAttribute = token.addAttribute(CharTermAttribute.class);
        result.add(termAttribute.toString());
      }
      stream.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public List<String> convert(String text) {
    List<String> result = new ArrayList<>();
    try {
      TokenStream stream = analyzer.tokenStream("contents", new StringReader(text));
      stream.reset();
      while (stream.incrementToken()) {
        AttributeSource token = stream.cloneAttributes();
        CharTermAttribute termAttribute = token.addAttribute(CharTermAttribute.class);
        result.add(termAttribute.toString());
      }
      stream.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }
}
