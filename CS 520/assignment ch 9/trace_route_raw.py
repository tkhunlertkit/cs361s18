import socket

class TCPPing(object):

    def __init__(self):
        self.ip_ver = 4
        self.ip_ihl = 5
        self.ip_tos = 0
        self.tot_len = 0 # Kernel Job
        self.ip_id = 54321
        self.ip_frag_off = 0
        self.ip_ttl = 255
        self.ip_proto = socket.IPPROTO_TCP
        self.ip_check = 0


def trace_route_raw(dst_name):
    source_ip = socket.gethostbyname(socket.gethostname())
    dst_addr = socket.gethostbyname(dst_name)
    print('From:', source_ip, source_ip)
    print('To:', dst_name, dst_addr)
    ip_ihl_ver = (self.ip_ver << 4) + self.ip_ihl
    try:
        s = socket.socket(socket.AF_INET, socket.SOCK_RAW, socket.IPPROTO_RAW)

    except socket.error as msg:
        print('Socket could not be created. Error Code :', str(msg[0]), 'Message', msg[1])
        exit()

if __name__ == '__main__':
    trace_route_raw('google.com')