package menu.view;

import menu.category.Category;
import menu.category.CategoryService;
import menu.coach.Coach;

import java.util.List;

public class OutputView {

    private static final String START_MESSAGE = "점심 메뉴 추천을 시작합니다.\n";
    private static final String END_MESSAGE = "추천을 완료했습니다.\n";

    private static final String SET_COACHES_NAME = "코치의 이름을 입력해 주세요. (, 로 구분)\n";
    private static final String SET_COACHES_CANNOT_EAT = "(이)가 못 먹는 메뉴를 입력해 주세요.\n";

    private static final String ERROR_MESSAGE_START = "[ERROR] ";
    private static final String ERROR_MESSAGE_SET_COACHES_NAME = "코치는 최소 2명 이상, 5명 이하 입력해야 합니다.\n";
    private static final String ERROR_MESSAGE_SET_COACHES_CANNOT_EATS = "못 먹는 메뉴는 최대 2개까지만 가능하며, 존재하는 메뉴여야 합니다.\n";

    private static final String RECOMMEND_RESULT_MESSAGE_START = "메뉴 추천 결과입니다.\n";
    private static final String RECOMMEND_RESULT_MESSAGE_WEEK = "[ 구분 | 월요일 | 화요일 | 수요일 | 목요일 | 금요일 ]\n";
    private static final String RECOMMEND_RESULT_MESSAGE_SEPARATOR = " | ";
    private static final String RECOMMEND_RESULT_MESSAGE_END = " ]\n";
    private static final String RECOMMEND_RESULT_MESSAGE_CATEGORY_START = "[ 카테고리 | ";
    private static final String RECOMMEND_RESULT_MESSAGE_COACH_MENU_START = "[ ";


    public static void printStartMessage() {
        System.out.println(START_MESSAGE);
    }

    public static void printEndMessage() {
        System.out.println(END_MESSAGE);
    }

    public static void printSetCoachesName() {
        System.out.println(SET_COACHES_NAME);
    }

    public static void printErrorMessage_setCoachesName() {
        System.out.println(ERROR_MESSAGE_START + ERROR_MESSAGE_SET_COACHES_NAME);
    }

    public static void printSetCoachesCannotEat(Coach coach) {
        System.out.println(coach.getName() + SET_COACHES_CANNOT_EAT);
    }

    public static void printErrorMessage_setCoachesCannotEats() {
        System.out.println(ERROR_MESSAGE_START + ERROR_MESSAGE_SET_COACHES_CANNOT_EATS);
    }

    public static void printRecommendResult(List<Coach> coaches) {
        StringBuilder result = new StringBuilder(RECOMMEND_RESULT_MESSAGE_START);
        result.append(RECOMMEND_RESULT_MESSAGE_WEEK);

        // 카테고리
        result.append(RECOMMEND_RESULT_MESSAGE_CATEGORY_START);
        List<Integer> recommendCategoryNumbers = CategoryService.getRecommendCategoryNumbers();
        for (int i = 0; i < recommendCategoryNumbers.size(); i++) {
            result.append(Category.getCategoryNameByNumber(recommendCategoryNumbers.get(i)));
            if (i != recommendCategoryNumbers.size() - 1) {
                result.append(RECOMMEND_RESULT_MESSAGE_SEPARATOR);
            }
        }
        result.append(RECOMMEND_RESULT_MESSAGE_END);

        // 코치, 코치별 메뉴
        for (Coach coach : coaches) {
            result.append(RECOMMEND_RESULT_MESSAGE_COACH_MENU_START);
            result.append(coach.getName());
            result.append(RECOMMEND_RESULT_MESSAGE_SEPARATOR);
            List<String> menusRecommended = coach.getMenusRecommended();
            for (int i = 0; i < menusRecommended.size(); i++) {
                result.append(menusRecommended.get(i));
                if (i != menusRecommended.size() - 1) {
                    result.append(RECOMMEND_RESULT_MESSAGE_SEPARATOR);
                }
            }
            result.append(RECOMMEND_RESULT_MESSAGE_END);
        }

        System.out.println(result);
    }

}
