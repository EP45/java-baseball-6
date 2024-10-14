package baseball;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class Application {
    public static void main(String[] args) {
        System.out.println("숫자 야구 게임을 시작합니다.");
        boolean inGame = true;
        while (inGame) {
            // Determine computer's number
            List<Integer> computer = new ArrayList<>();
            while (computer.size() < 3) {
                int randomNumber = Randoms.pickNumberInRange(1, 9);
                if (!computer.contains(randomNumber)) {
                    computer.add(randomNumber);
                }
            }

            boolean inRound = true;
            while (inRound) {
                // User input
                System.out.print("숫자를 입력해주세요 : ");
                String userGameInput = Console.readLine();
                List<Integer> user = new ArrayList<>();

                // Convert user's input into list of integers, while checking validity
                for (char digitChar : userGameInput.toCharArray()) {
                    if (!Character.isDigit(digitChar))
                        throw new IllegalArgumentException("숫자를 입력해야 합니다.");
                    if (user.contains(Character.getNumericValue(digitChar)))
                        throw new IllegalArgumentException("서로 다른 3자리의 수를 입력해야 합니다.");

                    user.add(Character.getNumericValue(digitChar));
                }
                if (user.size() != 3)
                    throw new IllegalArgumentException("서로 다른 3자리의 수를 입력해야 합니다.");

                // Debug
                System.out.println(computer);
                System.out.println(user);

                // Calculate score
                int strike = (int) IntStream.range(0, 3)
                        .filter(i -> Objects.equals(computer.get(i), user.get(i)))
                        .count();

                user.retainAll(computer);
                int ball = user.size() - strike;

                // Print score
                if (ball != 0)
                    System.out.printf("%d볼", ball);
                if (ball != 0 && strike != 0)
                    System.out.print(" ");
                if (strike != 0)
                    System.out.printf("%d스트라이크", strike);
                if (ball + strike == 0)
                    System.out.print("낫싱");
                System.out.print("\n");

                if (strike == 3) {
                    System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
                    inRound = false;
                }
            }

            System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
            String userCmdInput = Console.readLine();
            int parsedInput;

            try {
                parsedInput = Integer.parseInt(userCmdInput);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("숫자 1 또는 2를 입력해야 합니다.");
            }

            if (parsedInput != 1 && parsedInput != 2)
                throw new IllegalArgumentException("답변은 1과 2중 하나를 입력해야 합니다.");

            if (parsedInput == 2)
                inGame = false;
        }
    }
}
