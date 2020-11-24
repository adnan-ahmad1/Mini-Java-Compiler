int main() {
    One a = new One()
    a.get()
}

main:
    // line 1 assembly for main


One$$:
    quad 0
    quad One$$get
    quad one$$set