package me.muheun.util;

import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class Paging<T> implements Serializable {

  private int page; // 현재 페이지 번호
  private int rowSize; // 화면에 보여질 게시물 수.
  private int pageSize; // 화면에 보여질 페이지 수.
  private long totalCount; // 총 모델 갯수

  private int startRowNum; // 현재 페이지에서 시작 모델번호
  private int endRowNum; // 현재 페이지에서 마지막 모델번호

  private int startPage; // 시작 페이지
  private int endPage; // 끝 페이지
  private int firstPage; // 현 블럭에서 첫 페이지
  private int prevPage; // 이전 페이지
  private int nextPage; // 다음 페이지
  private int lastPage; // 현재 블럭에서 마지막 페이지

  private List<String> conditions;
  private String condition;
  private String keyword;

  private List<T> body;

  public Paging() {
    this(1, 10);
  }

  public Paging(int page) {
    this(page, 10);
  }

  public Paging(int pageNo, int size) {
    this.body = ListUtil.EMPTY;
    this.page = pageNo;
//        this.allCount = count;
    this.rowSize = size;
    this.pageSize = 10;
    this.conditions = ListUtil.newList();
  }

  public Paging<T> calcPaging(int elementCount) {

    int totalPage = 0;
    long allCount = elementCount;
    int nowPage = this.getPage();

    int startRowNum = 0;
    int endRowNum = 0;

    // 페이지 계산에 따른 블럭 설정.
    int totalBlock = 0;
    int nowBlock = 0;

    int firstPage = 0;
    int prevPage = 0;
    int nextPage = 0;
    int lastPage = 0;

    int startPage = 0;
    int endPage = 0;

    // 페이징 로직에 따른 조회 인덱스(RowNum)를 설정.
    if (allCount > 0) {
      totalPage = (int) (allCount / rowSize); // 전체 페이지 수 계산
      if (totalPage * rowSize < allCount) { // 버림으로 인해 마지막 페이지 계산이 안될 경우
        // +1
        totalPage++;
      }
      if (nowPage > totalPage) { // 요청 페이지가 전체 페이지의 범위를 벗어 나면
        nowPage = totalPage; // 전체 페이지로 설정
      }
      startRowNum = (nowPage - 1) * rowSize; // 시작레코드번호
      endRowNum = nowPage * rowSize; // 종료레코드번호

      if (endRowNum > allCount) { // endRowNum이 전체 게시물 수의 범위를 벗어 나면
        endRowNum = (int) allCount; // 전체 게시물 수로 설정
      }

      // 페이지 계산에 따른 블럭 설정.
      totalBlock = (totalPage - 1) / pageSize;
      nowBlock = (nowPage - 1) / pageSize;

      firstPage = 0;
      prevPage = 0;
      nextPage = 0;
      lastPage = 0;

      if (nowBlock > 0) {
        firstPage = 1;
      }

      if (nowPage > 1) {
        prevPage = nowPage - 1;
      }

      startPage = nowBlock * pageSize + 1;
      endPage = pageSize * (nowBlock + 1);

      if (endPage > totalPage) {
        endPage = totalPage;
      }

      if (nowPage < totalPage) {
        nextPage = nowPage + 1;
      }
      if (nowBlock < totalBlock) {
        lastPage = totalPage;
      }
    }

//		if (log.isDebugEnabled()) {
//
//			StringBuilder sb = new StringBuilder();
//
//			sb.append("\n현재 페이지 : " + nowPage);
//			sb.append("\n시작 Index : " + startRowNum);
//			sb.append("\n종료 Index : " + endRowNum);
//			sb.append("\n시작 페이지(전체) : " + startPage);
//			sb.append("\n종료 페이지(전체) : " + endPage);
//			sb.append("\n첫 페이지(블럭) : " + startRowNum);
//			sb.append("\n이전 페이지(블럭) : " + prevPage);
//			sb.append("\n다음 페이지(블럭) : " + nextPage);
//			sb.append("\n종료 페이지(블럭) : " + lastPage);
//
//			log.debug(sb.toString());
//		}

    this.page = nowPage;

    this.startRowNum = startRowNum; // 시작레코드 번호 주입
    this.endRowNum = endRowNum; // 종료레코드 번호 주입

    this.startPage = startPage; // 전체 페이지에서 시작 페이지
    this.endPage = endPage; // 전체 페이지에서 마지막 페이지

    this.firstPage = firstPage; // 현재 블럭에서 첫 페이지
    this.prevPage = prevPage; // 현재 블럭에서 이전 페이지
    this.nextPage = nextPage; // 현재 블럭에서 다음 페이지
    this.lastPage = lastPage; // 현재 블럭에서 마지막 페이지
    this.totalCount = allCount; // 총 갯수

    return this;
  }

}
