 class Solution {
	public int check(int[][][] matrix, int[][] answer, boolean[][][] clear, boolean[][] dnums) {
		boolean[][] row = new boolean[9][10];
		boolean[][] cul = new boolean[9][10];
		boolean[][] sq = new boolean[9][10];
		int count = 81;
		for (int i = 0; i < 9; i++) {
		int a=(i / 3) * 3;
			for (int j = 0; j < 9; j++) {
				int x = answer[i][j];
				if (x > 0) {
					count--;
					if (row[i][x] == true || cul[j][x] == true || sq[a+ (j / 3)][x] == true) {
						return -1;
					} else {
						row[i][x] = true;
						cul[j][x] = true;
						sq[a + (j / 3)][x] = true;
					}
				}
			}
		}
		int numcount;
		for (int i = 0; i < 9; i++) {
			numcount = 9;
			int x = 0;
			for (int k = 1; k < 10; k++) {
				if (row[i][k] == true) {
					numcount--;
				} else
					x = k;
			}
			if (numcount == 1) {
				for (int j = 0; j < 9; j++) {
					if (answer[i][j] == 0) {
						if (insert(matrix, answer, clear, i, j, x) == 1) {
							check(matrix,answer,clear,dnums);
							count--;
							cul[j][x] = true;
							sq[(i / 3) * 3 + (j / 3)][x] = true;
							
						} else
							count = -10;
					}
				}
			}
		}
		for (int i = 0; i < 9; i++) {
			numcount = 9;
			int x = 0;
			for (int k = 1; k < 10; k++) {
				if (cul[i][k] == true) {
					numcount--;
				} else
					x = k;
			}
			if (numcount == 1) {
				for (int j = 0; j < 9; j++) {
					if (answer[j][i] == 0) {
						if (insert(matrix, answer, clear, j, i, x) == 1) {
							check(matrix,answer,clear,dnums);
							count--;
							row[i][x] = true;
							sq[(i / 3) * 3 + (j / 3)][x] = true;
						} else
							count = -10;
					}
				}
			}
		}

		for (int i = 0; i < 9; i++) {
			numcount = 9;
			int num = 0;
			for (int k = 1; k < 10; k++) {
				if (sq[i][k] == true) {
					numcount--;
				} else
					num = k;
			}
			if (numcount == 1) {
				int x = (i / 3) * 3;
				int y = (i % 3) * 3;
				for (int a = 0; a < 3; a++) {
					for (int b = 0; b < 3; b++) {
						if (answer[x + a][y + b] == 0) {
							if (insert(matrix, answer, clear, x + a, y + b, num) == 1) {
								check(matrix,answer,clear,dnums);
								row[x + a][num] = true;
								cul[y + b][num] = true;
								count--;
							} else
								count = -10;
						}
					}

				}

			}
		}
		count(matrix, answer, clear);
		doublenum(matrix, answer, clear, dnums);
		return count;
	}

	public void doublenum(int[][][] matrix, int[][] answer, boolean[][][] clear, boolean[][] dnums) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (answer[i][j] == 0) {
					for (int k = 1; k < 10; k++) {
						if (!dnums[(i / 3) * 3 + (j / 3)][k]) {
							if (matrix[i][j][k] == 2) {
								int x = (i / 3) * 3;
								int y = (j / 3) * 3;
								for (int a = 0; a < 3; a++) {
									for (int b = 0; b < 3; b++) {
										if (matrix[x + a][y + b][k] == 2 && (x + a != i || y + b != j)) {
											if (x + a == i) {
											//	System.out.println((x + a) + " " + (y + b) + " " + k);
												rowclear(matrix, answer, clear, (x + a), y + b, k);
												a = 3;
												b = 3;
											} else if (y + b == j) {
												culclear(matrix, answer, clear, x + a, y + b, k);
												a = 3;
												b = 3;
											}
											dnums[(i / 3) * 3 + (j / 3)][k] = true;
										}

									}
								}

							} else if (matrix[i][j][k] == 3) {
								int y = (j / 3) * 3;
								int x = (i / 3) * 3;
								int rowcount = 2;
								int culcount = 2;
								for (int a = 0; a < 3; a++) {
									for (int b = 0; b < 3; b++) {
										if (matrix[x + a][y + b][k] == 3 && (x + a != i || y + b != j)) {
											if (x + a == i) {
												if (rowcount == 1) {
													dnums[(i / 3) * 3 + (j / 3)][k] = true;
													rowclear(matrix, answer, clear, x + a, y + b, k);
													b = 3;
													a = 3;
												} else {
													rowcount--;
												}
											} else if (y + b == j) {
												if (culcount == 1) {
													culclear(matrix, answer, clear, x + a, y + b, k);
													a = 3;
													b = 3;
													dnums[(i / 3) * 3 + (j / 3)][k] = true;
												} else
													culcount--;
											}
										}
									}
								}

							}
						}
					}
				}
			}
		}
	}

	public void rowclear(int[][][] matrix, int[][] answer, boolean[][][] clear, int r, int c, int num) {
		int x = ((c / 3) + 1) * 3;
		int y = (r / 3) * 3;
		int a=y + (r + 1) % 3;
		int b=y + (r + 2) % 3;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				if (!clear[r][(x + j) % 9][num]) {
					for (int k = 0; k < 3; k++) {
						matrix[a][(x + k) % 9][num]--;
						matrix[b][(x + k) % 9][num]--;
					}
					clear[r][(x + j) % 9][num] = true;
				}
			}
			x = ((c / 3) + 2) * 3;
		}
	}

	public void culclear(int[][][] matrix, int[][] answer, boolean[][][] clear, int r, int c, int num) {
		int x = (c / 3) * 3;
		int y = ((r / 3) + 1) * 3;
		int a=x + (c + 1) % 3;
		int b=x + (c + 2) % 3;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				if (!clear[(y + j) % 9][c][num]) {
					for (int k = 0; k < 3; k++) {
						matrix[(y + k) % 9][a][num]--;
						matrix[(y + k) % 9][b][num]--;
					}
					clear[(y + j) % 9][c][num] = true;
				}
			}
			y = ((r / 3) + 2) * 3;
		}
	}

	public boolean duckCheck(int[][] answer, int r, int c, int num) {
		for (int a = 0; a < 9; a++) {
			if (answer[r][a] == num || answer[a][c] == num) {
				return false;
			}
		}
		int x = (r / 3) * 3;
		int y = (c / 3) * 3;
		for (int a = 0; a < 3; a++) {
			for (int b = 0; b < 3; b++) {
				if (answer[x + a][y + b] == num) {
					return false;
				}
			}
		}
		return true;
	}

	public int insert(int[][][] matrix, int[][] answer, boolean[][][] clear, int r, int c, int num) {
		if (answer[r][c] > 0) {
			//System.out.println("이미 등록된 좌표" + r + "행 " + c + "열" + num + " 에러");
			return -1;
		}
		for (int a = 0; a < 9; a++) {
			if (answer[r][a] == num || answer[a][c] == num) {
				//System.out.println("열이나행에이미등록된숫자" + r + "행 " + c + "열" + num + " 에러");
				return -1;
			}
		}

		int x = (r / 3) * 3;
		int y = (c / 3) * 3;
		for (int a = 0; a < 3; a++) {
			for (int b = 0; b < 3; b++) {
				if (answer[x + a][y + b] == num) {
					//System.out.println("스퀘어에이미등록된숫자" + r + "행 " + c + "열" + num + " 에러");
					return -2;
				}
			}
		}
		// System.out.println(81-count+"번쨰로"+rin+"행 "+cin +"열에 "+num+" 인서트됨");
		answer[r][c] = num;
		for (int i = 0; i < 9; i++) {
			if (i == num) {
				matrix[i][c][num] = 0;
				matrix[r][i][num] = 0;
			}
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (x + i != r || y + j != c) {
					for (int k = 1; k < 10; k++) {
						if (k == num) {
							matrix[x + i][y + j][k] = 0;
						} else if (!clear[r][c][k]) {
							matrix[x + i][y + j][k]--;
						}
					}
				} else if (x + i == r && y + j == c) {
					for (int k = 1; k < 10; k++) {
						matrix[x + i][y + j][k] = 0;
					}
				}
			}
		}
		for (int k = 1; k < 10; k++) {
			clear[r][c][k] = true;
		}
		rowclear(matrix, answer, clear, r, c, num);
		culclear(matrix, answer, clear, r, c, num);
		return 1;
	}

	public void count(int[][][] matrix, int[][] answer, boolean[][][] clear) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int x = answer[i][j];
				if (x == 0) {
					for (int k = 1; k < 10; k++) {
						if (matrix[i][j][k] == 1) {
							//System.out.println("(" + i + ", " + j + ") " + k + "경우의수1");
							insert(matrix, answer, clear, i, j, k);
							count(matrix,answer,clear);
						}
					}
				}
			}
		}
	}

	public int[][] backTracking(int[][][] matrix, int[][] answer, boolean[][][] clear, boolean[][] dnums) {
		int[][] duckduck = new int[9][9];
		for (int i = 0; i < 9; i++) {
			duckduck[i] = answer[i].clone();
		}
		int[][][] duckmatrix= new int[9][9][10];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				duckmatrix[i][j]=matrix[i][j].clone();
			}
			}
		
		int duckNumber = 1;
		for (int duck = 2; duck < 7; duck++) {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					for (int k = 1; k < 10; k++) {
						if (duckmatrix[i][j][k] == duck && duckduck[i][j] == 0) {
							if (duckCheck(duckduck, i, j, k)) {
								
								//System.out.println(duckNumber);
								duckNumber++;
								duckduck[i][j] = k;
							//	System.out.println("덕덕" + duck + "들어간 오리는 " + i + " " + j + " " + k);
								if (jisungMoo(duckduck, matrix,1)) {
								///	System.out.println("무지성트루");
								//	System.out.println("(" + i + ", " + j + ") " + k + "무지성결과값");
									insert(matrix, answer, clear, i, j, k);
									int duckmoo=check(matrix, answer, clear, dnums);
									int tmp=duckmoo;
									while (duckmoo > 0) {
										tmp=duckmoo;
										duckmoo=check(matrix, answer, clear, dnums);
										//System.out.println(tmp+"템프");
										if(duckmoo==tmp) {
											break;
										}
									}
									
									if(duckmoo>0) {
										return backTracking(matrix, answer, clear, dnums);
									}
										return answer;
									} else {
									duckduck[i][j] = 0;
									duckmatrix[i][j][k] = 0;
								}
							}
						}
					}
				}
			}
		}
		return answer;
	}

	public boolean jisungMoo(int[][] answer, int[][][] matrix, int x) {
		if (x == 10) {
			return false;
		}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (answer[i][j] == 0) {
					for (int k = x; k < 10; k++) {
						if (matrix[i][j][k] > 0) {
							if (duckCheck(answer,i,j,k)) {
								answer[i][j] = k;
								if (jisungMoo(answer, matrix, 1)) {
									return true;
								}else {
									answer[i][j] = 0;
									return (jisungMoo(answer, matrix, k + 1));
								}
							}
						}
					}
				//	System.out.println(i + " " + j + " " + x);
					return false;
				}
			}
		}
		
		if(moocheck(answer)) {
			return true;
		}
		return false;
	}

	public boolean moocheck(int[][] answer) {
		boolean[][] row = new boolean[9][10];
		boolean[][] cul = new boolean[9][10];
		boolean[][] sq = new boolean[9][10];
		int a;
		for (int i = 0; i < 9; i++) {
			a=(i / 3) * 3;
			for (int j = 0; j < 9; j++) {
				int x = answer[i][j];
				if (x > 0) {
					if (row[i][x] == true || cul[j][x] == true || sq[a + (j / 3)][x] == true) {
						return false;
					} else {
						row[i][x] = true;
						cul[j][x] = true;
						sq[a + (j / 3)][x] = true;
					}
				}
			}
		}
		
		return true;
	}

     
    public void solveSudoku(char[][] board) {
        		int[][][] matrix = new int[9][9][10];
		boolean[][][] clear = new boolean[9][9][10];
		boolean[][] dnums = new boolean[9][10];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				for (int k = 1; k < 10; k++) {
					matrix[i][j][k] = 9;
				}
			}
		}
        int[][] answer  = new int[9][9];
                for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
                int a=board[i][j]-48;
                if (a>0){
                    insert(matrix,answer,clear,i,j,a);
                }
            }   
            }
        
        	int duckCount = 81;
		while (duckCount > 0) {
			int tmp = duckCount;
			duckCount = check(matrix, answer, clear, dnums);
		//	System.out.println(duckCount);
			if (duckCount == tmp) {
				break;
			}
		 }
        
        
        
        if (jisungMoo(answer,matrix,1)){
                    for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				board[i][j]=(char)(answer[i][j]+48);			
			}
  }

        }
        
        

		}
	}
    
