public class ReverseAnArray {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        int arr[]={1,2,3,4,5};
        int n=arr.length;
        int iterate=n/2;
        int i=0;
        n=arr.length-1;
        while(i<iterate){
            int k=arr[i];
            arr[i]=arr[n];
            arr[n--]=k;
            i++;
        }
        for(int e:arr){
            System.out.print(e);
        }
    }
}