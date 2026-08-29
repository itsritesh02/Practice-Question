//Brute Force

// Time Complexity  → O(2ⁿ)
// Space Complexity → O(n)


class StockProfit {

  static int maxProfit(int[] prices) {
    return solve(prices, 0, false);
  }

  static int solve(int[] prices, int i, boolean buy) {

    if (i == prices.length) {
      return 0;
    }

    // Skip
    int skip = solve(prices, i + 1, buy);

    int take;

    if (buy) {
      // Sell
      take = prices[i] + solve(prices, i + 1, false);
    } else {
      // Buy
      take = -prices[i] + solve(prices, i + 1, true);
    }

    return Math.max(skip, take);
  }

  public static void main(String[] args) {

    int[] prices = { 7, 1, 5, 3, 6, 4 };

    System.out.println(maxProfit(prices));
  }
}



//Optimized - Greedy Approach
// Time Complexity→O(n)
// Space Complexity → O(1)


// class StockProfit {

//   static int maxProfit(int[] prices) {

//     int profit = 0;

//     for (int i = 1; i < prices.length; i++) {

//       if (prices[i] > prices[i - 1]) {

//         profit += prices[i] - prices[i - 1];
//       }
//     }

//     return profit;
//   }

//   public static void main(String[] args) {

//     int[] prices = { 7, 1, 5, 3, 6, 4 };

//     System.out.println(maxProfit(prices));
//   }
// }
