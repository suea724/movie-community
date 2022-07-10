package com.project.movie.member;

import lombok.Data;

@Data
public class Pagination {

    private int currentPage; // 현재 페이지 번호
    private int totalCount; // 전체 게시물 수
    private int beginPage; // 시작 페이지 번호
    private int endPage; // 끝 페이지 번호
    private int blockRows; // 한 페이지 당 게시물 수
    private int blockSize = 10; // 한 블럭 내 페이지 수
    boolean prev; // 이전 페이지 표시 여부
    boolean next; // 다음 페이지 표시 여부

    public Pagination(int currentPage, int totalCount, int blockRows, int blockSize) {
        this.currentPage = currentPage;
        this.totalCount = totalCount;
        this.blockRows = blockRows;
        this.blockSize = blockSize;
        paging();
    }

    private void paging() {
//        beginPage = (currentPage / (blockSize + 1)) * blockSize + 1; // 21 페이지부터 beginPage = 21
//        endPage = beginPage + blockSize - 1;

        endPage=((int)Math.ceil(currentPage / (double)blockRows)) * blockRows;
        beginPage = endPage - (blockRows - 1);

        int totalPages = totalCount / blockRows + 1;

        if (totalPages < endPage) {
            endPage = totalPages;
            next = false;
        } else {
            next = true;
        }
        prev = (beginPage == 1 ? false : true);
    }

}