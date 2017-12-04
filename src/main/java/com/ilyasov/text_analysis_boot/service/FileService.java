package com.ilyasov.text_analysis_boot.service;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author damir
 */
public interface FileService {
  ArrayList<String> getPositiveWords();
  ArrayList<String> getNegativeWords();
}
