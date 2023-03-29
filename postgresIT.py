from locust import HttpUser, TaskSet, task, between

class PostgresTask(TaskSet):

    @task
    def postgresService(self):
        with self.client.get("/postgres", catch_response=True) as response:
            if response.status_code == 200:
#                 print("Response body: {}".format(response.content))
                response.success()
            else:
                response.failure()

class PerformanceTesting(HttpUser):
    """
    Postgres class that does requests to the locust web server running on localhost
    """

    host = "http://127.0.0.1:8080"
    wait_time = between(1, 2)
    tasks = [PostgresTask]