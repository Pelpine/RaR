package kr.book.action;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import kr.controller.Action;
import kr.rar.vo.BookVO;

public class BookSearchServlet implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String sheck = request.getParameter("sheck");//검색어
		 ArrayList<BookVO> list = new ArrayList<>();
	    	String key = "ttbtkdrl45620853001";
	    	String sk = "Title";//검색종류 - Keyword: 제목,저자 , Title:제목 , Author:저자 , Publisher:출판사
	    	int start = 1; //검색결과 시작페이지
	    	int maxresults = 20; //검색결과 한페이지당 최데 출력 개수
	        String urlStr = "https://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey="+key+"&Query="+sheck+"&QueryType="+sk+"&MaxResults="+maxresults+"&start="+start+"&SearchTarget=Book&output=xml&Cover=MidBig";
	        URL url = new URL(urlStr);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");

	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        Document doc = builder.parse(conn.getInputStream());
	        NodeList itemList = doc.getElementsByTagName("item");
	        for (int i = 0; i < itemList.getLength(); i++) {
	            Node itemNode = itemList.item(i);
	            if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
	                Element itemElement = (Element) itemNode;
	                String title = itemElement.getElementsByTagName("title").item(0).getTextContent();
	                String author = itemElement.getElementsByTagName("author").item(0).getTextContent();
	                String pubDate = itemElement.getElementsByTagName("pubDate").item(0).getTextContent();
	                String uel = itemElement.getElementsByTagName("cover").item(0).getTextContent();
	                
	                BookVO vo = new BookVO();
	                vo.setTitle(title);
	                vo.setAuthor(author);
	                vo.setPubDate(pubDate);
	                vo.setCoverUrl(uel);
	                list.add(vo);
	            }
	        }
	        
	        request.setAttribute("book", list);
		return "/WEB-INF/views/book/bookForm.jsp";
	}
    
}

