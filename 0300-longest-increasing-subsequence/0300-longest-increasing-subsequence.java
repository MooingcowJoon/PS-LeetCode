class Solution {
    class Node{
        int val;
        Node child;
        Node(int val){
            this.val=val;
        }
    }
    public int lengthOfLIS(int[] nums) {
        int[] arr = nums;
      Node root = new Node(arr[0]);
        int max=0;
        Node node;
        int a;
        for (int x: arr){
            a=1;
            node = root;
            while(node.val<x){
                if(node.child==null){
                    node.child=new Node(x);
                }
                node=node.child;
                a++;
            }
                node.val=x;
            max=Math.max(a,max);
        }
    return max;
    
    }
    }

