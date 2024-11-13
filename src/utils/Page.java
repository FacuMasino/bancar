package utils;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {
    private int currentPage;
    private int pageSize;
    private int totalElements;
    private int totalPages;
    private List<T> content;
    private int startElementPos;
    private int endElementPos;
	
	public Page(int currentPage, int pageSize, List<T> fullList) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalElements = fullList.size();
        this.totalPages = (int) Math.ceil((double) totalElements / pageSize);
        this.content = paginateContent(fullList);
	}

    private List<T> paginateContent(List<T> fullContent) {
    	// Posicion inicial del nuevo array
        startElementPos = (currentPage - 1) * pageSize;
        // Posicion final del nuevo array (excluyente)
        endElementPos = Math.min(startElementPos + pageSize, totalElements);

        return new ArrayList<>(fullContent.subList(startElementPos, endElementPos));
    }

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotalElements() {
		return totalElements;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public List<T> getContent() {
		return content;
	}

	public int getStartElementPos() {
		return startElementPos;
	}

	public int getEndElementPos() {
		return endElementPos;
	}
   
}
