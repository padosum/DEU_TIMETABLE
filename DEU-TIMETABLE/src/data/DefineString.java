package data;

public class DefineString {
   
   public static final String APP_NAME = "동의대 시간표 프로그램";
   public static final double Version = 0.1;
   public static final String TITLE = String.format("%s Version %1.1f", APP_NAME, Version);
   public static final String SEARCH = "검색";
   public static final String ADD = "추가";
   public static final String REMOVE = "제거";
   public static final String NO_FILE = "파일이 존재하지 않습니다.";
   public static final String WARNING = "안돼요";
   
   public static class AboutThis {
      public static final String TITLE = "이 프로그램에 대하여";
      /*public static final String MESSAGE = "마음대로 쓰세요<br>" +
            "프로램정보 <a href=\"http://alloc.tistory.com/212\">보러가기</a><br>" +
            "다운로드 <a href=\"http://dev.naver.com/projects/pknutimetable/download\">받으러가기</a><br>" +
            "만든사람 <a href=\"http://alloc.tistory.com/\">블로그</a>";*/
   }
   
   public static class Menu {
      public static final String HELP = "도움말(H)";
      public static final String ABOUT = AboutThis.TITLE;
      public static final String FILE = "파일(F)";
      public static final String SAVE_TIME_TABLE = "시간표 저장(S)";
      public static final String SAVE_TIME_IMG = "시간표 저장(이미지)";
      public static final String LOAD_TIME_TABLE = "시간표 불러오기(L)";
      public static final String LOAD_HAND_BOOK = "강의편람 불러오기";
      public static final String LOAD_CURRICULUM = "교육과정 불러오기";
      public static final String EVALUATION = "강의평가";
   }
   
   
   
   public static class Parser {
      public static final String TIME = "시간";
      public static final String GRADE = "학년";
      public static final String LECTURE_NAME = "교과목명";
      public static final String LECTURE_NUM = "강좌번호";
      public static final String LECTURE_ROOM = "강의실 및 강의시간";
      public static final String PROFESSOR = "담당교수";
      public static final String CREDIT = "학점";
      public static final String SORT = "구분";
      public static final String LEARNING_BASE = "학문기초";
      public static final String EVAL = "강의평점";
   }
   
   public static class Week {
      public static final String TIME = "요일/시간";
      public static final String MON = "월";
      public static final String TUE = "화";
      public static final String WEN = "수";
      public static final String THU = "목";
      public static final String FRI = "금";
      public static final String SAT = "토";
      public static final String SUN = "일";
   }
   


}