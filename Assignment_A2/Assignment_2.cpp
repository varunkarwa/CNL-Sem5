#include<iostream>
#include<math.h>
#include<time.h>
using namespace std;
class Hamming_code
{
	int msg[30];
	int code_sent[30];
	int code_received[30];
	char parity;
	int data_bits,parity_bits;
	public:
		Hamming_code()
		{
			for(int i=0;i<30;i++)
			{
				msg[i]=code_sent[i]=code_received[i]=0;
			}
			data_bits=parity_bits=0;
			parity='E';
		}
		void cal_pbits()
		{
			cout<<"Enter type of Parity(E/O):";
			cin>>parity;
			cout<<"Enter number of data bits:";
			cin>>data_bits;
			while(data_bits+parity_bits+1>pow(2,parity_bits))
			{
				parity_bits++;
			}
			cout<<"No of parity bits: "<<parity_bits<<endl;
			cout<<"Total number of data bits:"<<parity_bits+data_bits<<endl;
		}
		void read_message()
		{
			cout<<"Enter Message:";
			for(int i=1;i<=data_bits;i++)
				cin>>msg[i];
			cout<<"Message entered is:";
			for(int i=1;i<=data_bits;i++)
				cout<<msg[i];
			cout<<endl;
		}
		void encode_msg()
		{
			int d=0,p=1;
			for(int i=1;i<=data_bits+parity_bits;i++)
			{
				if(i==pow(2,d))
				{
					code_sent[i]=0;
					d++;
				}
				else
				{
					code_sent[i]=msg[p];
					p++;
				}
			}
			p=0;
			int min,max=0,bit_sum,k,j;
			for(int i=1;i<=data_bits+parity_bits;i=pow(2,p))
			{
				p++;
				bit_sum=0;
				k=i;
				min=1;
				max=i;
				for(j=i;j<=data_bits+parity_bits;j=k+i)
				{
					for(k=j;max>=min&&k<=data_bits+parity_bits;++min,++k)
					{
						if(code_sent[k] == 1)
						bit_sum++;
					}
					min=1;
				}
				if(parity=='E')
				{
					if(bit_sum%2==0)
						code_sent[i]=0;
					else
						code_sent[i]=1;
				}
				else
				{
					if(bit_sum%2!=0)
						code_sent[i]=0;
					else
						code_sent[i]=1;
				}
			}
		}
		void sent_print()
		{
			cout<<"Code Sent with";
			if(parity=='E')
				cout<<" Even Parity:";
			else
				cout<<" Odd Parity:";
			for(int i=1;i<=data_bits+parity_bits;i++)
				{
					cout<<code_sent[i];
				}
			cout<<endl;
		}
		void get_received()
		{
			for(int i=1;i<=data_bits+parity_bits;i++)
				code_received[i]=code_sent[i];
		}
		void disturbance()
		{
			srand(time(0));
			int i = rand()%(data_bits+parity_bits)+1;
			if(code_received[i]==1)
				code_received[i]=0;
			else
				code_received[i]=1;
		}
		void print_received()
		{
			cout<<"Code received with:";
			if(parity=='E')
				cout<<" Even Parity:";
			else
				cout<<" Odd Parity:";
			for(int i=1;i<=data_bits+parity_bits;i++)
				cout<<code_received[i];
		}
		bool equal()
		{
			for(int i=1;i<=data_bits+parity_bits;i++)
				if(code_sent[i]!=code_received[i])
					return false;
			return true;
		}
		void error_checking()
		{
			int p=0;
			int min,max=0,bit_sum,j,k;
			int code[10]={0};
			int q=1;
			for(int i=1;i<=data_bits+parity_bits;i=pow(2,p))
			{
				p++;
				bit_sum=0;
				j=i;
				k=i;
				min=1;
				max=i;
				for(j;j<=data_bits+parity_bits;)
				{
					for(k=j;max>=min&&k<=data_bits+parity_bits;++min,++k)
					{
						if(code_received[k]==1)
							bit_sum++;
					}
					j=k+i;
					min=1;
				}
				if(parity=='E')
				{
					if(bit_sum%2==0)
						code[q]=0;
					else
						code[q]=1;
				}
				else
				{
					if(bit_sum%2!=0)
						code[q]=0;
					else
						code[q]=1;
				}
				q++;
			}
			int error=0;
			int p1=0;
			for(int l=1,p1=0;l<q;l++,p1++)
			{
				error+=code[l]*pow(2,p1);
			}
			cout<<"Error is in bit no.:"<<error<<endl;
		}
};
int main()
{
	int choice;
	Hamming_code obj;
	do
	{
		cout<<"1.Sender Side\n2.Receiver Side\n3.Exit\n";
		cin>>choice;
		switch(choice)
		{
			case 1:
				cout<<"1.Without Disturbance\n2.With Disturbance\n";
				cin>>choice;
				obj.cal_pbits();
				obj.read_message();
				obj.encode_msg();
				obj.get_received();
				switch(choice)
				{
					case 1:
						obj.sent_print();
						break;
					case 2:
						obj.disturbance();
						obj.sent_print();
						break;
				}
				break;
			case 2:
				obj.print_received();
				if(obj.equal())
					cout<<"\nNo Error in received code."<<endl;
				else
					obj.error_checking();
				break;
		}
	}while(choice!=3);
}
