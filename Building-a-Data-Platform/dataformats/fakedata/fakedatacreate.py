__author__ = 'steveo'
from faker import Factory
#----------------------------------------------------------------------
def create_names(fake):
    """"""
    for i in range(10000000):
        print fake.first_name() \
        + "|" + fake.last_name() \
              + "|" + fake.email() \
              + "|" + fake.company() \
        + "|" + fake.job() \
        + "|" + fake.street_address() \
        + "|" + fake.city() \
        + "|" + fake.state_abbr() \
        + "|" + fake.zipcode_plus4() \
        + "|" + fake.url() \
        + "|" + fake.phone_number() \
        + "|" + fake.user_agent() \
        + "|" + fake.user_name() \
        + "|" + str(fake.unix_time()) \
        + "|" + str(fake.date_time_between(start_date="-70y", end_date="-20y"))
if __name__ == "__main__":
    fake = Factory.create()
    create_names(fake)