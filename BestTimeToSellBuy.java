
//Brute Force
// Time Complexity  → O(n²)
// Space Complexity → O(1)

public class BestTimeToSellBuy {

  static int profitMax(int prices[]) {

    int profit = 0;

    for (int i = 0; i < prices.length; i++) {

      for (int j = i + 1; j < prices.length; j++) {

        int current = prices[j] - prices[i];

        if (current > profit) {
          profit = current;
        }
      }
    }

    return profit;
  }

  public static void main(String[] args) {

    int[] prices = { 5, 2, 8, 1, 9, 4 };

    System.out.println("Maximum Profit: " + profitMax(prices));
  }
}




//Optimized-Greedy
// Time Complexity  → O(n)
// Space Complexity → O(1)


// public class BestTimeToSellBuy {
//   static int profitMax(int prices[]) {
//     int min = prices[0];
//     int profit = 0;
//     for (int i = 1; i < prices.length; i++) {
//       if (prices[i] < min) {
//         min = prices[i];
//       }
//       if (prices[i] - min > profit) {
//         profit = prices[i] - min;
//       }

//     }
//     return profit;
//   }

//   public static void main(String[] args) {

//     int[] prices = { 5, 2, 8, 1, 9, 4 };

//     System.out.println("Maximum Profit: " + profitMax(prices));

//   }
// }