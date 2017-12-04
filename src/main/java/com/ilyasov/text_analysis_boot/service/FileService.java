package com.ilyasov.text_analysis_boot.service;

import java.util.List;

/**
 * @author damir
 */
public interface FileService {
  List<String> getPositiveWords();

  List<String> getNegativeWords();
}
