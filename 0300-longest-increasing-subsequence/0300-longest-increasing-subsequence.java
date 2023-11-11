class Solution {

    public int lengthOfLIS(int[] nums) {
        int[] arr= nums;
        int n=nums.length;
        int[] bs=new int[n];
        int max=1;
        bs[0]=arr[0];
        for(int x: arr){
            if(bs[max-1]<x){
                bs[max]=x;
                max++;
            }else{
                int left = 0;
                int right = max-1;
                int mid=(left+right)/2;
                while(left < right){
                    mid=(left+right)/2;
                    if(bs[mid]==x){
                        left=mid;
                        break;
                    }else if(bs[mid]>x){
                        right=mid;
                    }else{
                        left=mid+1;
                    }
                }
                bs[left]=x;
            }
        }
        return max;
    }
}
