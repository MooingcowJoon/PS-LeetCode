class Solution {
    class Node{
        int val;
        Node child;
        Node(int val){
            this.val=val;
        }
    }
     int insert(Node node, int x){
        if(node.val>=x){
            node.val=x;
            return 0;
        }
        if(node.child==null){
            node.child=new Node(x);
            return 1;
        }
        return insert(node.child,x)+1;
    }
    public int lengthOfLIS(int[] nums) {
        
        int[] arr= nums;
        
             Node root = new Node(arr[0]);
        int max=0;
        for (int x: arr){
            max=Math.max(max,insert(root,x));
        }
        return max+1;
    }


}