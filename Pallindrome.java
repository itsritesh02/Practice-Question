class Pallindrome{
  static void loop(int n){
    for(int i=1; i<=n;i++){

      for(int j=i;j>=1;j--){
      System.out.print(j);
      }
for(int j=2;j<=i;j++){
  System.out.print(j);
}


      System.out.println();
    }
  }
  public static void main(String[] args) {
    int n=8;
    loop(n);
  }
}