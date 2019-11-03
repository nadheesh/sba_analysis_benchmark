import ast
import numpy as np

import urllib.request
input_json = urllib.request.urlopen("http://127.0.0.1:8090/get_stats").read().decode('UTF-8')


print('\n')

stats = ast.literal_eval(input_json)


dic = {}
for api_stats in stats:

	time_stamps = np.array(api_stats.get('timeStamps')).T
	response_times = time_stamps[1] - time_stamps[0]

	print("api name : %s and number of samples %d"%(api_stats.get('apiName'), api_stats.get("requestCount")))
	execution_time = api_stats.get("endTime") - api_stats.get("startTime")
	print("execution time = %d:%d ms" % (execution_time/1000/60, execution_time/1000%60))
	print("throughput = %.4f per sec" % (api_stats.get("requestCount")/execution_time * 1000))
	print("average latency = %.4f, %.4f" %(np.mean(response_times), np.mean(response_times[len(response_times)//10:])))
	dic[api_stats.get('apiName')] = api_stats.get('responseTimes')
	print("\n")




#print(dic)

#print(np.unique(np.array(dic.get('SAMPLE_APP')) - np.array((dic.get('PRIME_APP')) + np.array(dic.get('ECHO_APP')))))




