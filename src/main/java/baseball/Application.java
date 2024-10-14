package baseball;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Application {
    public static void main(String[] args) {
        System.out.println("숫자 야구 게임을 시작합니다.");
        boolean keepRunning = true;
        while (keepRunning) {
            // Determine computer's number
            List<Integer> computer = new ArrayList<>();
            while (computer.size() < 3) {
                int randomNumber = Randoms.pickNumberInRange(1, 9);
                if (!computer.contains(randomNumber)) {
                    computer.add(randomNumber);
                }
            }

            boolean inGame = true;
            while (inGame) {
                System.out.print("숫자를 입력해주세요 : ");
                String userGameInput = Console.readLine();
                int parsedInput;

                // Parse if number
                try {
                    parsedInput = Integer.parseInt(userGameInput);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("숫자를 입력해야 합니다.");
                }

                // Is it 3 digit?
                if (parsedInput < 100 || parsedInput > 999)
                    throw new IllegalArgumentException("서로 다른 3자리의 수를 입력해야 합니다.");

                // Check Duplication
                List<Integer> user = new ArrayList<>();
                for (int i = 100; i != 0; i/=10) {
                    int inputDigit = parsedInput / i % 10;
                    if (user.contains(inputDigit)) {
                        throw new IllegalArgumentException("서로 다른 3자리의 수를 입력해야 합니다.");
                    }
                    user.add(parsedInput / i % 10);
                }
                System.out.println(computer);
                System.out.println(user);

                // Compare with computer
                int strike = 0, ball = 0;
                for (int i = 0; i < 3; i++) {
                    if (Objects.equals(computer.get(i), user.get(i))) {
                        strike++;
                    }
                }
                for (int i = 0; i < 3; i++) {
                    if (user.contains(computer.get(i))) {
                        ball++;
                    }
                }
                ball -= strike;

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
                    inGame = false;
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
                keepRunning = false;
        }
    }
}
