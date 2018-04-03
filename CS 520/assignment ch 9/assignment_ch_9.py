import sys
import socket
import datetime

def main(dest_name):
    dest_addr = socket.gethostbyname(dest_name)
    port = 33434
    icmp = socket.getprotobyname('icmp')
    udp = socket.getprotobyname('udp')
    ttl = 1
    max_hops = 30
    while True:
        recv_socket = socket.socket(socket.AF_INET, socket.SOCK_RAW, icmp)
        send_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM, udp)
        send_socket.setsockopt(socket.SOL_IP, socket.IP_TTL, ttl)
        recv_socket.bind((b"", port))
        start = datetime.datetime.now()
        send_socket.sendto(b"", (dest_name, port))
        curr_addr = None
        curr_name = None
        try:
            _, curr_addr = recv_socket.recvfrom(512)
            endtime = datetime.datetime.now()
            curr_addr = curr_addr[0]
            try:
                curr_name = socket.gethostbyaddr(curr_addr)[0]
            except socket.error:
                curr_name = curr_addr
        except socket.error:
            pass
        finally:
            send_socket.close()
            recv_socket.close()

        if curr_addr is not None:
            curr_host = "%s (%s) %.7s ms" % (curr_name, curr_addr, (endtime - start).total_seconds() * 1000)
        else:
            curr_host = "*"
        print("%d\t%s" % (ttl, curr_host))

        ttl += 1
        if curr_addr == dest_addr or ttl > max_hops:
            break

if __name__ == "__main__":
    dest_url = 'google.com'
    if len(sys.argv) < 2:
        print('No Destination Specified, using google.com as the destination')

    print('Tracing route to', dest_url, '...')
    main(dest_url)
