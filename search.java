
import java.util.*;
import java.io.*;

class homework{

public static void main (String args[]) throws Exception
{
    
    ReadFile file = new ReadFile("input.txt"); 
    
       String []TextLines = file.OpenFile();
       
       System.out.println("Checking for input");
       for(int i =0 ;i<TextLines.length;i++)
       {
           System.out.println(TextLines[i]);
       }
       
       int NUMBEROFSUNDAYTRAFFICLINE = Integer.parseInt(TextLines[4+Integer.parseInt(TextLines[3])]);
       
       System.out.println("The number of sun lines are "+NUMBEROFSUNDAYTRAFFICLINE);
       
       String algo = TextLines[0];
       String startState = TextLines[1];
       String goalState = TextLines[2];
       int numTraLine = Integer.parseInt(TextLines[3]);
       
       String adj[][] = new String[NUMBEROFSUNDAYTRAFFICLINE+1][NUMBEROFSUNDAYTRAFFICLINE+1];
       
       for(int i=0;i<NUMBEROFSUNDAYTRAFFICLINE+1;i++)
       {
           for(int j=0; j<NUMBEROFSUNDAYTRAFFICLINE+1; j++)
           {
               adj[i][j] = "-1";
           }
       }
       
       /*
       System.out.println("ADJ matrix is");
       for(int i=0;i<numTraLine+1;i++)
       {
           for(int j=0; j<numTraLine+1; j++)
           {
               System.out.print(adj[i][j]);
           }
           System.out.println();
       }
       */
       
      // System.out.println("The num of traffice lines is "+numTraLine);
       
       int nodeCounter = 1;
       int z=0;                                                     //iterates thru input lines in the input file
       for(z =4 ;z< 4+numTraLine;z++)
       {
           
           String []inputLine = TextLines[z].split(" ");
           //System.out.println(inputLine[0] + " " +inputLine[1] + " "+inputLine[2] + " ");
           int i = -999;
           int flagi = 0;
           int j = -999;
           int flagj = 0;
           
           for( i=1; i<NUMBEROFSUNDAYTRAFFICLINE+1 ;i++)
           {
               if((adj[0][i]).equals(inputLine[0]) )
               {
                   flagi=1;
                   break;
               }
           }
           
           for( j=1; j<NUMBEROFSUNDAYTRAFFICLINE+1 ;j++)
           {
               if((adj[0][j]).equals(inputLine[1]) )
               {
                   flagj=1;
                   break;
               }
           }
           
           
           if(flagi == 1 && flagj == 1 )
           {
               System.out.println("Added " + adj[i][j]);
               adj[i][j] = inputLine[2]; 
           }
           
           else if (flagi == 1 && flagj ==0 )
           {
               adj[0][nodeCounter] = inputLine[1];
               adj[nodeCounter][0] = inputLine[1];
               adj[i][nodeCounter] = inputLine[2];
               nodeCounter++;
               
               System.out.println("Added " + inputLine[1]);
               
           }
           
           else if( flagi == 0 && flagj == 1)
           {
               adj[0][nodeCounter] = inputLine[0];
               adj[nodeCounter][0] = inputLine[0];
               adj[nodeCounter][j] = inputLine[2];
               nodeCounter++;
               
               System.out.println("Added " + inputLine[0]);
               
               
           }
           else
           {
               
                adj[0][nodeCounter] = inputLine[0];
                adj[nodeCounter][0] = inputLine[0];
                nodeCounter++;
                
                System.out.println("Added " + inputLine[0]);
               

           if (!(inputLine[0].equals(inputLine[1])))
           {
                adj[0][nodeCounter] = inputLine[1];
                adj[nodeCounter][0] = inputLine[1];
                nodeCounter++;
                
                System.out.println("Added " + inputLine[1]);
            }
                adj[nodeCounter-2][nodeCounter-1] = inputLine[2];
                
                
                
           } 
           
       }
       
       int numSunTraLine = Integer.parseInt(TextLines[z]);
       String sunTime[][] = new String[numSunTraLine][2];
       
       z++;
       int counterForSunTime = 0;
       for(int i=z; i<z+numSunTraLine;i++)
       {
           String []inputLine = TextLines[i].split(" ");
           // adj[inputLine[0]][inputLine[0]] = inputLine[1];
          
               sunTime[counterForSunTime][0] = inputLine[0];
               sunTime[counterForSunTime][1] = inputLine[1];
               counterForSunTime ++;
           
       }
       
       //System.out.println("The length of sunTime is" + sunTime.length);
       
       /*
       for(int i=0;i<sunTime.length;i++)
       {
           for(int j=0; j<2;j++)
           {
               System.out.print(sunTime[i][j]);
           }
           System.out.println();
       }
       */
       //printadj(adj, numTraLine);
       
      
      switch(algo)
      {
       
          case "BFS" :  BFS b = new BFS();
                        if(!startState.equals(goalState))
                        {
                            b.BFSQueue(adj,startState,goalState);
                        }
                        else
                        {
                            System.out.println(goalState + " " + 0);
                        }
                        break;
          case "DFS" :  DFS d = new DFS();
                        if(!startState.equals(goalState))
                        {
                            d.DFSStackStuff(adj, startState, goalState);
                        }
                        else
                        {
                            System.out.println(goalState + " " + 0);
                        }
                        
                        break;
          case "UCS" :  UCSwithPriorityQ u = new UCSwithPriorityQ();                              //Working fine
                        if(!(startState.equals(goalState)))
                        {
                            u.priorityQStuff(adj, startState, goalState);    
                        }
                        else
                        {
                            System.out.println(goalState + " " + 0);
                        }
                        break;//Working fine
          case "A*"  :  AStar a = new AStar();
                        if(!(startState.equals(goalState)))
                        {
                            a.AStarStuff(adj, startState, goalState, sunTime);
                        }
                        else
                        {
                            System.out.println(goalState + " " + 0);
                        }
                        break;
          //default : System.out.println("Fuck off");
     
      }
    
}    
       
    public static void printadj(String [][]adj, int numTraLine)
    {

        for(int i=0;i< numTraLine+1;i++)
        {
            for(int j=0;j<numTraLine+1;j++)
            {
                System.out.print(adj[i][j] + "\t");
            }
            System.out.println();

        }
    
    }
    
}


class ReadFile{

String path;

public ReadFile(String file_path)
{
path = file_path;
}

public int readLines() throws IOException
{

FileReader fr = new FileReader(path);
BufferedReader br = new BufferedReader(fr);
String tmpLine;
int numberOfLines = 0;
while ( (tmpLine = br.readLine()) != null)
{
numberOfLines++;
}
br.close();
return numberOfLines;
}

public String[] OpenFile() throws IOException
{
FileReader fr = new FileReader(path);
BufferedReader br = new BufferedReader(fr);

int numberOfLines = readLines();
String []textData = new String [numberOfLines];

for (int i=0;i<numberOfLines; i++)
{
	textData[i] = br.readLine();
}
br.close();
return textData;

}
}

class DFS {
    
    static int nodeNum = 1 ;
    static Stack<LinkNodeDFS> stackTable = new Stack<LinkNodeDFS>();
    public static void DFSStackStuff(String adj[][], String startNode, String endNode) throws IOException{
        
        System.out.println("In DFS");
        
        Stack<LinkNodeDFS> s1 = new Stack<LinkNodeDFS>();
        
        int startNodeIndex = 0;
        int visited[] = new int [adj.length];
        //System.out.println("The length of visited is "+visited.length);
        for(int i=0;i<visited.length;i++)
        {
            visited[i] = 0;
        }
        
        startNodeIndex = findIndex(adj,startNode);
        int parentNodeNum = 1;
        LinkNodeDFS a = new LinkNodeDFS(startNode,nodeNum);
        a.parentNodeNum = 0;
        nodeNum++;
        s1.add(a);
        stackTable.add(a);
        visited[startNodeIndex] = 1;
        
        while(!s1.empty())
        {
            LinkNodeDFS parent = s1.pop();
            System.out.println("The parent is " + parent.stateName);
            
            if(parent.stateName.equals(endNode))
            {
                System.out.println("Success");
                displayResultDFS(parent);
                break;
            }
            
            Stack childrenStack = findChildren(adj,parent,visited);
            while(!childrenStack.empty())
            {
                LinkNodeDFS child = (LinkNodeDFS)childrenStack.pop();
                s1.push(child);
                stackTable.push(child);
            }
            
            System.out.println("The content of stack is ");
            displayStack(s1);
        }
        
        System.out.println("The final stack table is");
        displayStack(stackTable);
        
    }
    
    public static Stack findChildren(String adj[][],LinkNodeDFS parent, int visited[])
    {
        Stack childrenStack = new Stack();
        int parentNodeIndex = findIndex(adj,parent.stateName);
        
        for(int j=1 ; j<adj.length;j++)
        {
            if(Integer.parseInt(adj[parentNodeIndex][j]) > 0 && visited[j]==0 )
            {
                LinkNodeDFS child = new LinkNodeDFS(adj[0][j], nodeNum);
                nodeNum++;
                child.parentNodeNum = parent.nodeNum;
                visited[j]=1;
                childrenStack.add(child);
            }
        }
        
        return childrenStack;
    }
    
    
     public static int findIndex(String adj[][], String s)
    {
        for(int i=1;i<adj.length;i++)
        {
            if(adj[i][0].equals(s))
            {
                return i;
            }

        }
        return -1;
    }
     
     
     public static void displayStack(Stack s)
     {
         Stack b = (Stack)s.clone();
         
         while(!b.empty())
         {
             LinkNodeDFS tmp = (LinkNodeDFS)b.pop();
             System.out.println(tmp.nodeNum+ " " + tmp.stateName + " " + tmp.parentNodeNum);
         }
     }
     
     public static void displayResultDFS(LinkNodeDFS endNode) throws IOException
     {
         LinkNodeDFS tmp = endNode;
         Stack<LinkNodeDFS> answer = new Stack<LinkNodeDFS>();
         while(tmp.parentNodeNum != 0)
         {
             answer.add(tmp);
             Stack<LinkNodeDFS> searchTable = (Stack)stackTable.clone();
             
             while(!searchTable.empty())
             {
                 LinkNodeDFS head = searchTable.pop();
                 if(tmp.parentNodeNum == head.nodeNum)
                 {
                    tmp=head;
                    break;
                 }
                 
             }
             
             
         }
         answer.add(tmp);
         
         System.out.println("The final answer is ");
         int depth = 0;
		 
		 FileWriter fwDFS = new FileWriter("output.txt");
		 PrintWriter pwDFS = new PrintWriter(fwDFS);
		 
         while(!answer.empty())
         {
             LinkNodeDFS ansNode = (LinkNodeDFS)answer.pop();
			 pwDFS.println(ansNode.stateName + " " + depth);
             System.out.println(ansNode.stateName + " " + depth);
             depth++;
         }
         
		 fwDFS.close();
		 
     }

}

class LinkNodeDFS
{
    //String parent;
    String stateName;
    int nodeNum;
    int parentNodeNum;
    LinkNode next;
    
    public LinkNodeDFS(String stateName, int nodeNum)
    {
        this.stateName = stateName;
        this.nodeNum = nodeNum;
    }
}

class UCSwithPriorityQ {
    
    static Comparator<LinkNode> c = new totalPathCostComparator();
    static PriorityQueue<LinkNode> queue = new PriorityQueue<LinkNode>(10,c);
    static PriorityQueue<LinkNode> visited = new PriorityQueue<LinkNode>(10,c); 
    static LinkedList<LinkNode> qTable = new LinkedList<LinkNode>();
    //static LinkedHashMap hm = new LinkedHashMap();
    //static Queue<LinkNode> finalResult = new LinkedList<LinkNode>();
    //static int visited[];
    
    static int nodeNum = 0;
    static int depth = 0;
    static int parentNodeNum = 0;
    
    public static void priorityQStuff(String adj[][], String startNode, String goalNode) throws IOException
    {
        
        //visited = new int[adj.length];
        
        LinkNode head = new LinkNode(nodeNum,-9999, startNode, 0);
        queue.add(head); //head 
        nodeNum++;
        depth++; 
        //parentNodeNum++;
        visited.add(head);
        qTable.add(head);
        //hm.put(head, null);
        
        
        //visited[findIndex(adj,startNode)]=1;
        
        // int headIndex = findIndex(adj,startNode);
        
        //addChildren(adj,headIndex);
        
        //displayQueue(queue);
        
        
        do{
            
            if(queue.size()==0)
            {
                System.out.println("Failure");
            }
            
            LinkNode currNode = queue.poll();
            System.out.println("The current node is"+currNode.state);
            if( currNode.state.equals(goalNode))
            {
                System.out.println("Success is " + currNode.state + "PARENT NODE AS" + currNode.parentNodeNum);
                displayResult(currNode, qTable);
                break;
            }
            
            //int currentNodeIndex = findIndex(adj,currNode.state);
            
            Queue children =  findChildren(adj,findIndex(adj,currNode.state),currNode);
            parentNodeNum++;
            //LinkNode originalHead = queue.peek();
            
            while(children.size()!=0)
            {
                System.out.println("In children while loop size" + children.size());
                //displayQueue(children);
                
             
                LinkNode child = (LinkNode)children.remove();
                System.out.println("The child is " + child.state);
                
                if(!queue.contains(child) & !visited.contains(child))
                {
                    System.out.println("In IF");
                    queue.add(child);
                    qTable.add(child);
                    //visited.add(child);
                    System.out.println("Added child in the queue is " + child.state + " with path cost" + child.totalPathCost );
                    PriorityQueue<LinkNode> queueForDisplay = new PriorityQueue<LinkNode>(queue);
                    displayPriorityQueue(queueForDisplay);
                }
                else if(queue.contains(child))
                {
                    System.out.println("In else if queue.contains(child)");
                    
                    Iterator iter = queue.iterator();
                    while(iter.hasNext())
                    {
                        LinkNode tmp = (LinkNode)iter.next();
                        if(tmp.state.equals(child.state))
                        {
                            System.out.println("The tmp node found in queue is "+tmp.state);
                            if(child.totalPathCost < tmp.totalPathCost)
                            {
                                
                                System.out.println("Node removed from queue " + tmp.state);
                                queue.remove(tmp);
                                //hm.remove(tmp);
                                qTable.remove(tmp);
                                System.out.println("Node added to queue " + child.state);
                                queue.add(child);
                                qTable.add(child);
                                
                                //hm.put(child, currNode);
                                PriorityQueue<LinkNode> queueForDisplay = new PriorityQueue<LinkNode>(queue);
                                displayPriorityQueue(queueForDisplay);
                
                            }
                            break;
                        }
                    }
                }
                else if(visited.contains(child))
                {
                    System.out.println("In else if visited.contains(child) ");
                    
                    Iterator iter = queue.iterator();
                    while(iter.hasNext())
                    {
                        LinkNode tmp = (LinkNode)iter.next();
                        if(tmp.state.equals(child.state))
                        {
                            System.out.println("The tmp node found in queue is "+tmp.state);
                            if(child.totalPathCost < tmp.totalPathCost)
                            {
                                System.out.println("Node removed from visited " + tmp.state);
                                visited.remove(tmp);
                                qTable.remove(tmp);
                                System.out.println("Node added to queue "+ child.state);
                                queue.add(child);
                                qTable.add(child);
                                PriorityQueue<LinkNode> queueForDisplay = new PriorityQueue<LinkNode>(queue);
                                displayPriorityQueue(queueForDisplay);
                            }
                            break;
                        }
                    }
                }
                
                
                
            }
            //addChildren(adj,currentNodeIndex);
            visited.add(currNode);
        }while(queue.size()!=0);
        
        //displayHashMap(hm);
        
        //System.out.println("The QTable is ");
        //displayLL(qTable);
        
        //Queue<LinkNode> q = new LinkedList<LinkNode>(qTable);
        //displayQueue(q);      
    }
    
     public static int findIndex(String adj[][], String s)
    {
        for(int i=1;i<adj.length;i++)
        {
            if(adj[i][0].equals(s))
            {
                return i;
            }

        }
        return -1;
    }
    
     public static Queue findChildren (String adj[][], int parentIndex, LinkNode currNode)
     {
         Queue<LinkNode> answer = new LinkedList<LinkNode>();
         
         for(int j=1;j<adj.length;j++)
         {
             if( Integer.parseInt(adj[parentIndex][j])>0 && !visited.contains(new LinkNode(0,0,adj[0][j],0)))
             {
                 LinkNode child = new LinkNode(nodeNum,currNode.nodenum, adj[0][j],currNode.totalPathCost + Integer.parseInt(adj[parentIndex][j]));
                 answer.add(child);
                 nodeNum++;
                 //hm.put(child,currNode);
                 //visited.add(child);
             }
             
         }
         
        
         return answer;
     }
     
     
     
     
     
     /*
     public static void addChildren (String adj[][], int parentIndex)
     {
         for(int j=1;j<adj.length;j++)
         {
             if(Integer.parseInt(adj[parentIndex][j]) > 0 && visited[j] == 0)
             {
                 LinkNode child = new LinkNode(nodeNum,adj[0][j],Integer.parseInt(adj[parentIndex][j]),depth, parentNodeNum);
                 queue.add(child);
                 nodeNum++;
                 visited[j]=1;
                 
             }
         }
         
         
     }
     */
     
     public static void displayPriorityQueue(PriorityQueue<LinkNode> q)
     {
         System.out.print("The priority queue is : ");
         while(q.size()!=0)
         {
             LinkNode tmp = q.poll();
             System.out.print(tmp.state + " " + tmp.totalPathCost + "\t");
         }
         System.out.println();
         
     }
     
      public static void displayQueue(Queue<LinkNode> q)
     {
         System.out.println("The queue is");
         while(q.size()!=0)
         {
             LinkNode tmp = q.remove();
             System.out.println(tmp.state + " with TPC " + tmp.totalPathCost + " with parent "+tmp.parentNodeNum );
         }
         
     }
      
    /*  public static void displayHashMap(LinkedHashMap hm)
      {
          System.out.println("The HashMap is ");
          Set s = hm.entrySet();
          Iterator i = s.iterator();
          while(i.hasNext())
          {
              Map.Entry me = (Map.Entry)i.next();
              LinkNode tmpK = (LinkNode)me.getKey();
              System.out.print(tmpK.state + ": ");
              
              LinkNode tmpV = (LinkNode)me.getValue();
              
              try
              {
                  System.out.println(tmpV.state + " " + tmpV.totalPathCost);
              }
              catch(Exception e)
              {
                  System.out.println("Null");
              }
             
          }
      }
    */
      
      public static void displayLL(LinkedList n)
      {
          for(int i=0; i<n.size();i++)
          {
              LinkNode tmp = (LinkNode)n.get(i);
            System.out.println (tmp.nodenum + "\t" + tmp.state + " \t " + tmp.totalPathCost + "\t" + tmp.parentNodeNum);
          }
      }
      
      
      public static void displayResult(LinkNode n, Queue qTable) throws IOException
      {
          
          LinkedList<LinkNode> tmpll = new LinkedList<LinkNode>();
          LinkNode tmp = n;
          tmpll.add(tmp);
          
          while(tmp.parentNodeNum!=-9999)
          {
              System.out.println(tmp.parentNodeNum + " is the parent node num");
              Queue<LinkNode> q = new LinkedList<LinkNode>(qTable);
              
              System.out.println("Seaching the element");
              while(q.size()!=0)
              {
              LinkNode ll = (LinkNode)q.remove();
              System.out.println("The popped element is " + ll.state);
              if(tmp.parentNodeNum == ll.nodenum)
              {
                  tmp = ll;
                  tmpll.add(tmp);
                  //q = qTable;
                  break;
              
              }
              }
          
          }
          
          //displayLL(tmpll);
		  
		  FileWriter fwUCS = new FileWriter("output.txt");
		  PrintWriter pwUCS = new PrintWriter(fwUCS);
          
          for(int i = tmpll.size()-1; i>=0;i--)
          {
              LinkNode displayNode = (LinkNode)tmpll.get(i);
			  pwUCS.println(displayNode.state + " " + displayNode.totalPathCost);
              System.out.println(displayNode.state + "\t" + displayNode.totalPathCost);
          }
          
          fwUCS.close();
      }
      
      
      
}

class totalPathCostComparator implements Comparator<LinkNode>
{
    public int compare(LinkNode x, LinkNode y)
    {
        if(x.totalPathCost > y.totalPathCost)
        {
            return 1;
        }
        else if(x.totalPathCost < y.totalPathCost)
        {
            return -1;
        }
        return 0;
    }
}

class LinkNode
{
    int nodenum;
    String state;
    int totalPathCost;
    int depth;
    int parentNodeNum;
    
    public LinkNode(int nodenum, int parentNodeNum, String state, int totalPathCost )
    {
        this.nodenum = nodenum;
        this.state = state;
        this.totalPathCost = totalPathCost;
       // this.depth = depth;
        this.parentNodeNum = parentNodeNum;
    }
    
}


 class BFS {
     
    static HashMap hm = new HashMap();
    
    public static void BFSQueue(String adj[][], String startNode, String goalNode) throws IOException
    {
        System.out.println("In BFS");
        
        Queue q1 = new LinkedList();
       
        int startNodeIndex = 0;
        int visited[] = new int [adj.length];
        for(int i=0;i<visited.length;i++)
        {
            visited[i] = 0;
        }
        
        startNodeIndex = findIndex(adj,startNode);
        
        q1.add(adj[startNodeIndex][0]);
        hm.put(adj[startNodeIndex][0], "null");
        visited[startNodeIndex]=1;
        displayQueue(q1);
        displayVisited(visited);
        
        System.out.println("The start node index is" + startNodeIndex);
        
        q1 = enqueueChildren(adj, q1, startNodeIndex, visited);

        System.out.println("After enqueue");
       
        displayQueue(q1);
        displayVisited(visited);
        q1 = dequeue(q1);
        
        System.out.println("After dequeue");
        displayQueue(q1);
            
            Iterator it = q1.iterator();
            while(it.hasNext())           
            {
                
                q1 = enqueueChildren(adj, q1, findIndex(adj, q1.element().toString()), visited );
               
                 System.out.println("After enqueue");
                 displayQueue(q1);
                 displayVisited(visited);
                q1 = dequeue(q1);
                
                if(q1.toString().equals(goalNode))
                {
                    System.out.println("Success");
                    displayResultBFS(hm, q1.toString());
                    break;
                }
                System.out.println("After dequeue");
                 displayQueue(q1);

            }
            
            displayHashMap(hm);
            displayResultBFS(hm, goalNode);
 
    }
    
    
    public static void displayQueue(Queue q1)
    {
           
                Iterator itt = q1.iterator();
                while(itt.hasNext())
                {
                    System.out.print((String)itt.next());
                }
                System.out.println();    
    }
    
    public static int findIndex(String adj[][], String s)
    {
        for(int i=1;i<adj.length;i++)
        {
            if(adj[i][0].equals(s))
            {
                return i;
            }

        }
        return -1;
    }
    
    public static Queue enqueueChildren(String adj[][], Queue q1, int index, int visited[])
    {
        for(int j=1;j<adj.length;j++)
        {
            if( Integer.parseInt(adj[index][j]) > 0 && visited[j] ==0)
            {
                q1.add(adj[0][j]);
                hm.put(adj[0][j], adj[index][0]);
                visited[j] =1;
            }
        }
        
        return q1;
    }
    
    public static Queue dequeue (Queue q1)
    {
        q1.remove();
        return q1;
    }
    
    
    public static void displayVisited(int visited[])
    {
        for(int i=0;i<visited.length;i++)
        {
            System.out.print(i +  "\t");
        }
        System.out.println();
        
        for(int i=0;i<visited.length;i++)
        {
            
            System.out.print(visited[i] + "\t");
        }
        System.out.println();
        
    }
    
    public static void displayHashMap(HashMap hm)
    {
        String result[] = new String[2*hm.size()];
        int i=0;
        System.out.println("The hashmap is");
        Set set = hm.entrySet();
        Iterator ite = set.iterator();
      
      
      // Display elements
      while(ite.hasNext()) {
         Map.Entry mapentry = (Map.Entry)ite.next();
         System.out.print(mapentry.getKey() + ": ");
         
          result[i]=mapentry.getKey().toString();
          i++;
          if(mapentry.getValue() != null)
          {
              result[i]=mapentry.getValue().toString();
          }
          else
          {
              result[i]="null";
          }
          i++;
         System.out.println(mapentry.getValue());
         
      }
      System.out.println();
      
      
     /* System.out.println("This is the result:");
      
     
      
      for(int p=0;p<result.length;p++)
      {
          System.out.print(result[p] + " \t");
          p++;
          System.out.print(result[p]);
          System.out.println();
      }
      
     */
    }
    
    public static void displayResultBFS(HashMap hm, String goalNode) throws IOException
    {
     
     System.out.println("Displaying result");
        
     String hashValue = goalNode;
     
     Stack s = new Stack();
     
     s.add(goalNode);
     
     while(!(hashValue.equals("null")))
     {
        s.add(getHashValue(hm,hashValue)); 
       // System.out.println(getHashValue(hm,hashValue) + " is the hashvalue");
        hashValue = getHashValue(hm,hashValue);
        
     }
     
      s.pop();                // Removing null
      int depth = 0;
      
      
      
      FileWriter fw = new FileWriter("output.txt") ;
          
      PrintWriter pw = new PrintWriter(fw);
     
      while(s.size()!=0)
      {
          
          pw.println(s.pop() + " " + depth);
          //System.out.println(s.pop() + " "+ depth);
          depth++;
      }
      
      fw.close();
       
      
    }
    
    public static String getHashValue(HashMap hm, String key)
    {
        Set set = hm.entrySet();
        Iterator ite = set.iterator();
        int flag = 0;
        String answer = new String();
        
        while(ite.hasNext())
        {
            Map.Entry mapentry = (Map.Entry)ite.next();
            if(mapentry.getKey().equals(key))
            {
                flag=1;
                answer = mapentry.getValue().toString();
                break;
            }
        }
        
      return answer;
        
    }
    
}




class AStar {
    
    static Comparator<LinkNodeAStar> c = new totalPathCostComparatorAStar();
    static PriorityQueue<LinkNodeAStar> queue = new PriorityQueue<LinkNodeAStar>(10,c);
    static PriorityQueue<LinkNodeAStar> visited = new PriorityQueue<LinkNodeAStar>(10,c); 
    static LinkedList<LinkNodeAStar> qTable = new LinkedList<LinkNodeAStar>();
    //static LinkedHashMap hm = new LinkedHashMap();
    //static Queue<LinkNodeAStar> finalResult = new LinkedList<LinkNodeAStar>();
    //static int visited[];
    
    static int nodeNum = 0;
    static int depth = 0;
    static int parentNodeNum = 0;
    
    public static void AStarStuff(String adj[][], String startNode, String goalNode, String sunTime[][]) throws IOException
    {
        
        //visited = new int[adj.length];
        
        LinkNodeAStar head = new LinkNodeAStar(nodeNum,-9999, startNode, 0);
        head.h = findSunTime(startNode,sunTime);
        head.f = head.totalPathCost + head.h ;
        queue.add(head); //head 
        nodeNum++;
        depth++; 
        //parentNodeNum++;
        visited.add(head);
        qTable.add(head);
        //hm.put(head, null);
        
        //System.out.println(findSunTime(startNode,sunTime) + " is the sunTime of root");
        
        //visited[findIndex(adj,startNode)]=1;
        
        // int headIndex = findIndex(adj,startNode);
        
        //addChildren(adj,headIndex);
        
        //displayQueue(queue);
        
        
        do{
            
            if(queue.size()==0)
            {
                System.out.println("Failure");
            }
            
            LinkNodeAStar currNode = queue.poll();
            System.out.println("The current node is"+currNode.state);
            if( currNode.state.equals(goalNode))
            {
                System.out.println("Success is " + currNode.state);
                displayResult(currNode, qTable);
                break;
            }
            
            //int currentNodeIndex = findIndex(adj,currNode.state);
            
            Queue children =  findChildren(adj,findIndex(adj,currNode.state),currNode, sunTime);
            parentNodeNum++;
            //LinkNodeAStar originalHead = queue.peek();
            
            while(children.size()!=0)
            {
                System.out.println("In children while loop size" + children.size());
                //displayQueue(children);
                
             
                LinkNodeAStar child = (LinkNodeAStar)children.remove();
                System.out.println("The child is " + child.state);
                
                if(!queue.contains(child) & !visited.contains(child))
                {
                    System.out.println("In IF");
                    queue.add(child);
                    qTable.add(child);
                    //visited.add(child);
                    System.out.println("Added child in the queue is " + child.state + " with path cost" + child.totalPathCost );
                    PriorityQueue<LinkNodeAStar> queueForDisplay = new PriorityQueue<LinkNodeAStar>(queue);
                    displayPriorityQueue(queueForDisplay);
                }
                else if(queue.contains(child))
                {
                    System.out.println("In else if queue.contains(child)");
                    
                    Iterator iter = queue.iterator();
                    while(iter.hasNext())
                    {
                        LinkNodeAStar tmp = (LinkNodeAStar)iter.next();
                        if(tmp.state.equals(child.state))
                        {
                            System.out.println("The tmp node found in queue is "+tmp.state);
                            if(child.totalPathCost < tmp.totalPathCost)
                            {
                                
                                System.out.println("Node removed from queue " + tmp.state);
                                queue.remove(tmp);
                                //hm.remove(tmp);
                                qTable.remove(tmp);
                                System.out.println("Node added to queue " + child.state);
                                queue.add(child);
                                qTable.add(child);
                                
                                //hm.put(child, currNode);
                                PriorityQueue<LinkNodeAStar> queueForDisplay = new PriorityQueue<LinkNodeAStar>(queue);
                                displayPriorityQueue(queueForDisplay);
                
                            }
                            break;
                        }
                    }
                }
                else if(visited.contains(child))
                {
                    System.out.println("In else if visited.contains(child) ");
                    
                    Iterator iter = queue.iterator();
                    while(iter.hasNext())
                    {
                        LinkNodeAStar tmp = (LinkNodeAStar)iter.next();
                        if(tmp.state.equals(child.state))
                        {
                            System.out.println("The tmp node found in queue is "+tmp.state);
                            if(child.totalPathCost < tmp.totalPathCost)
                            {
                                System.out.println("Node removed from visited " + tmp.state);
                                visited.remove(tmp);
                                qTable.remove(tmp);
                                System.out.println("Node added to queue "+ child.state);
                                queue.add(child);
                                qTable.add(child);
                                PriorityQueue<LinkNodeAStar> queueForDisplay = new PriorityQueue<LinkNodeAStar>(queue);
                                displayPriorityQueue(queueForDisplay);
                            }
                            break;
                        }
                    }
                }
                
                
                
            }
            //addChildren(adj,currentNodeIndex);
            visited.add(currNode);
        }while(queue.size()!=0);
        
        //displayHashMap(hm);
        
        //System.out.println("The QTable is ");
        //displayLL(qTable);
        
        //Queue<LinkNodeAStar> q = new LinkedList<LinkNodeAStar>(qTable);
        //displayQueue(q);      
    }
    
     public static int findIndex(String adj[][], String s)
    {
        for(int i=1;i<adj.length;i++)
        {
            if(adj[i][0].equals(s))
            {
                return i;
            }

        }
        return -1;
    }
    
     public static Queue findChildren (String adj[][], int parentIndex, LinkNodeAStar currNode, String sunTime[][])
     {
         Queue<LinkNodeAStar> answer = new LinkedList<LinkNodeAStar>();
         
         for(int j=1;j<adj.length;j++)
         {
             if( Integer.parseInt(adj[parentIndex][j])>0 && !visited.contains(new LinkNodeAStar(0,0,adj[0][j],0)))
             {
                 LinkNodeAStar child = new LinkNodeAStar(nodeNum,currNode.nodenum, adj[0][j],currNode.totalPathCost + Integer.parseInt(adj[parentIndex][j]));
                 child.h = findSunTime(child.state,sunTime);
                 child.f = child.totalPathCost + child.h;
                 answer.add(child);
                 nodeNum++;
                 //hm.put(child,currNode);
                 //visited.add(child);
             }
             
         }
         
        
         return answer;
     }
     
     public static void displayPriorityQueue(PriorityQueue<LinkNodeAStar> q)
     {
         System.out.print("The priority queue is : ");
         while(q.size()!=0)
         {
             LinkNodeAStar tmp = q.poll();
             System.out.print(tmp.state + " " + tmp.totalPathCost + "\t" + tmp.h +" is the heuristic " + tmp.f + " is f");
         }
         System.out.println();
         
     }
     
      public static void displayQueue(Queue<LinkNodeAStar> q)
     {
         System.out.println("The queue is");
         while(q.size()!=0)
         {
             LinkNodeAStar tmp = q.remove();
             System.out.println(tmp.state + " with TPC " + tmp.totalPathCost + " with parent "+tmp.parentNodeNum );
         }
         
     }
    
      public static void displayLL(LinkedList n)
      {
          for(int i=0; i<n.size();i++)
          {
              LinkNodeAStar tmp = (LinkNodeAStar)n.get(i);
            System.out.println (tmp.nodenum + "\t" + tmp.state + " \t " + tmp.totalPathCost + "\t" + tmp.parentNodeNum);
          }
      }
      
      
      public static void displayResult(LinkNodeAStar n, Queue qTable) throws IOException
      {
          
          LinkedList<LinkNodeAStar> tmpll = new LinkedList<LinkNodeAStar>();
          LinkNodeAStar tmp = n;
          tmpll.add(tmp);
          
          while(tmp.parentNodeNum!=-9999)
          {
              System.out.println(tmp.parentNodeNum + " is the parent node num");
              Queue<LinkNodeAStar> q = new LinkedList<LinkNodeAStar>(qTable);
              
              System.out.println("Seaching the element");
              while(q.size()!=0)
              {
              LinkNodeAStar ll = (LinkNodeAStar)q.remove();
              System.out.println("The popped element is " + ll.state);
              if(tmp.parentNodeNum == ll.nodenum)
              {
                  tmp = ll;
                  tmpll.add(tmp);
                  //q = qTable;
                  break;
              
              }
              }
          
          }
          
          //displayLL(tmpll);
          
		  FileWriter fwAStar = new FileWriter("output.txt");
		  PrintWriter pwAStar = new PrintWriter(fwAStar);
		  
          for(int i = tmpll.size()-1; i>=0;i--)
          {
              LinkNodeAStar displayNode = (LinkNodeAStar)tmpll.get(i);
			  pwAStar.println(displayNode.state + " "+ displayNode.totalPathCost);
              System.out.println(displayNode.state + "\t" + displayNode.totalPathCost);
          }
		  
		  fwAStar.close();
          
          
      }
      
      public static int findSunTime(String node, String sunTime[][])
      {
         /* for(int j=0;j<sunTime.length;j++)
          {
              System.out.println(sunTime[j][0] + "\t" + sunTime[j][1]);
          }
          */
          int i;
          for( i=0;i<sunTime.length;i++)
          {
              if(sunTime[i][0].equals(node))
              {
                  break;
              }
          }
          return Integer.parseInt(sunTime[i][1]);
      }
      
      
}

class totalPathCostComparatorAStar implements Comparator<LinkNodeAStar>
{
    public int compare(LinkNodeAStar x, LinkNodeAStar y)
    {
        if(x.f > y.f)
        {
            return 1;
        }
        else if(x.f < y.f)
        {
            return -1;
        }
        return 0;
    }
}

class LinkNodeAStar
{
    int nodenum;
    String state;
    int totalPathCost;
    int depth;
    int parentNodeNum;
    int h ;
    int f ;
    
    public LinkNodeAStar(int nodenum, int parentNodeNum, String state, int totalPathCost )
    {
        this.nodenum = nodenum;
        this.state = state;
        this.totalPathCost = totalPathCost;
       // this.depth = depth;
        this.parentNodeNum = parentNodeNum;
    }
    
}


