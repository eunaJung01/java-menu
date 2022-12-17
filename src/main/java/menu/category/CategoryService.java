package menu.category;

import camp.nextstep.edu.missionutils.Randoms;
import menu.coach.Coach;
import menu.coach.CoachService;
import menu.view.OutputView;
import org.assertj.core.util.Sets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryService {

    private static List<Integer> recommendCategoryNumbers = new ArrayList<>(); // 주별 카테고리 추천 (숫자로 저장)

    // 못 먹는 메뉴 기반 랜덤 추천
    public static void recommendMenu() {
        List<Coach> coaches = CoachService.getCoaches();
        recommendCategories();
        for (Coach coach : coaches) {
            List<String> recommendMenusToCoach = setRecommendMenusToCoach(coach);
            coach.setRecommendedMenu(recommendMenusToCoach);
        }
        OutputView.printRecommendResult(coaches);
    }

    // 카테고리 랜덤 추천 (한 주에 같은 카테고리는 최대 2회)
    private static void recommendCategories() {
        List<Integer> recommendCategories = new ArrayList<>();
        while (true) {
            for (int i = 0; i < 5; i++) {
                recommendCategories.add(Randoms.pickNumberInRange(1, 5));
            }
            Set<Integer> recommendCategoryNumbers = Sets.newHashSet(recommendCategories);
            if (recommendCategoryNumbers.size() > 3) break; // 추천 카테고리 숫자들에 대한 set 개수가 3 이하 -> 3회 이상 중복된 것
            recommendCategories = new ArrayList<>(); // break 하지 않을 경우 초기화
        }
        recommendCategoryNumbers = recommendCategories;
    }

    // 코치별 메뉴 랜덤 추천 (중복되지 않도록)
    private static List<String> setRecommendMenusToCoach(Coach coach) {
        List<String> recommendMenus = new ArrayList<>();
        List<String> cannotEats = coach.getMenusCannotEats();

        for (Integer recommendCategoryNumber : recommendCategoryNumbers) {
            List<String> menusOfCategory = Category.getCategoryMenusByNumber(recommendCategoryNumber);
            String randomMenu;
            while (true) {
                int randomNumber = Randoms.pickNumberInRange(1, menusOfCategory.size() - 1);
                randomMenu = menusOfCategory.get(randomNumber);
                if (!isCannotEats(cannotEats, randomMenu) && !isDuplicateRecommendMenu(recommendMenus, randomMenu)) {
                    break;
                }
            }
            recommendMenus.add(randomMenu);
        }
        return recommendMenus;
    }

    // 못 먹는 메뉴인지 확인
    private static boolean isCannotEats(List<String> cannotEats, String randomMenu) {
        for (String cannotEat : cannotEats) {
            if (cannotEat.compareTo(randomMenu) == 0) {
                return true;
            }
        }
        return false;
    }

    // 메뉴 추천 중복 확인
    private static boolean isDuplicateRecommendMenu(List<String> recommendMenus, String randomMenu) {
        for (String recommendMenu : recommendMenus) {
            if (recommendMenu.compareTo(randomMenu) == 0) {
                return true;
            }
        }
        return false;
    }

}
