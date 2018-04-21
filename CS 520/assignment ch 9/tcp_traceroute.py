import socket
import struct
import datetime

def trace_route(dest_name):
    dest_addr = socket.gethostbyname(dest_name)
    icmp = socket.getprotobyname('icmp')
    udp = socket.getprotobyname('udp')
    print(dest_addr)
    ttl = 1
    port = 80
    done = False
    timeout = 0.01

    while not done:
        r = socket.socket(socket.AF_INET, socket.SOCK_RAW, icmp)
        r.bind((b'', port))
        r.settimeout(timeout)
        # st = socket.socket(socket.AF_INET, socket.SOCK_DGRAM, udp)
        # st.setsockopt(socket.SOL_IP, socket.IP_TTL, ttl)
        # st.sendto(b'', (dest_addr, port))
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.setsockopt(socket.SOL_IP, socket.IP_TTL, ttl)
        s.settimeout(timeout)
        curr_addr = ''
        curr_name = ''
        endtime = None
        start = None

        tries = 2
        # while tries > 0:
        try:
            start = datetime.datetime.now()
            s.connect((dest_addr, port))
            # time between connect to get round trip time.
        except socket.timeout:
            endtime = datetime.datetime.now()

            # ttl += 1
            # print('Failed at connect: ttl = %02d' % ttl)
            # continue

        # try to send data, if there is no broken pip exception, then we have reached the destination.
        
        try:
            s.send(b'')
            done = True
            curr_addr = socket.gethostbyname(dest_name)
            curr_name = dest_name

            endtime = datetime.datetime.now()
        except socket.timeout:
            # if an excetion is raised, then look for the icmp packet.
            try:
                _, curr_addr = r.recvfrom(512)
                endtime = datetime.datetime.now()
                curr_addr = curr_addr[0]
                # print(curr_addr)
                try:
                    curr_name = socket.gethostbyaddr(curr_addr)[0]
                except socket.herror as err:
                    curr_name = '*'
                # break
            except socket.timeout:
                curr_name = 'Error retrieving'
                # _, curr_addr = r.recvfrom(512)
                # curr_addr = curr_addr[0]
                print(curr_addr)

        # tries -= 1
        print(ttl, curr_addr, curr_name, (endtime - start).total_seconds() * 1000)
        if curr_addr == dest_addr:
            break
        s.close()
        r.close()
        # st.close()
        ttl += 1

if __name__ == '__main__':
    trace_route('google.com')