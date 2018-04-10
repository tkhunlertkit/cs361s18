import socket
import struct

def trace_route(dest_name):
    dest_addr = socket.gethostbyname(dest_name)
    icmp = socket.getprotobyname('icmp')
    udp = socket.getprotobyname('udp')
    print(dest_addr)
    ttl = 1
    port = 80

    while True:
        r = socket.socket(socket.AF_INET, socket.SOCK_RAW, icmp)
        r.bind((b'', port))
        r.settimeout(1)
        # st = socket.socket(socket.AF_INET, socket.SOCK_DGRAM, udp)
        # st.setsockopt(socket.SOL_IP, socket.IP_TTL, ttl)
        # st.sendto(b'', (dest_addr, port))
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.setsockopt(socket.SOL_IP, socket.IP_TTL, ttl)
        s.connect((dest_addr, port))
        # tries = 3
        curr_addr = ''
        # while tries > 0:
        try:
            _, curr_addr = r.recvfrom(512)
            curr_addr = curr_addr[0]
            # print(curr_addr)
            try:
                curr_name = socket.gethostbyaddr(curr_addr)[0]
            except socket.herror as err:
                curr_name = '*'
            # break
        except socket.timeout:
            curr_name = 'Error retrieving'
            break
        except socket.error:
            pass
        # tries -= 1
        print(ttl, curr_addr, curr_name)
        if curr_addr == dest_addr:
            break
        s.close()
        r.close()
        # st.close()
        ttl += 1
    # s.send(b'')
    # _, curr_addr = s.recv(512)

if __name__ == '__main__':
    trace_route('google.com')