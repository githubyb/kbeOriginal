/*
 * Copyright 2005-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.nwpu.dmpm.kbe.webservice.springws.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.nwpu.dmpm.kbe.webservice.springws.dto.PDMData;
import edu.nwpu.dmpm.kbe.webservice.springws.service.EchoService;

/**
 * Default implementation of <code>EchoService</code>.
 *
 * @author Ingo Siebert
 * @author Arjen Poutsma
 */
@Service
public class EchoServiceImpl implements EchoService {

/*	@Autowired
	private DataService dataService;
*/	
    public String echo(String s) {
        return "2014"+s;
    }
   
   public String fileUploadFeedback(String fileId){
	  /*return dataService.saveFileUploadFeedback(fileId);*/
	   return null;
   }
   
   public List<PDMData> getPDMDatas(String prodNo,String type){
	   /*List<PDMData> datas=new ArrayList<PDMData>();
	   
	   List<Map<String, Object>> lm=dataService.getPDMDatas(type);
	   for(Map<String, Object> m: lm){
		   PDMData data1=new PDMData();
		   data1.setId(m.get("ID").toString());
		   data1.setCode(m.get("CODE").toString());
		   data1.setVer(m.get("VER").toString());
		   data1.setValidity(m.get("VALIDITY").toString());
		   datas.add(data1);
	   }
	   return datas;*/
	   return null;
   }
   public PDMData getPDMData(String id){
	  /* Map<String, Object> m=dataService.getPDMData(id);
	   PDMData data1=new PDMData();
	   data1.setId(m.get("ID").toString());
	   data1.setCode(m.get("CODE").toString());
	   data1.setVer(m.get("VER").toString());
	   data1.setValidity(m.get("VALIDITY").toString());
		
	   return data1;*/
	   return null;
   }
}
