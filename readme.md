## MACF: A novel method for multiple nucleotide sequence alignment based on clustering and FM-index
[website](http://lab.malab.cn/~cjt/MSA)

### Sequence clustering

[CLUSTAR](https://github.com/Guuhua/CLUSTAR)

### Usage

```shell
usage: java -jar jmsa-**.jar  [-m] mode [-i] path [-o] path

  necessary arguments: 
    -i  Input file path (nucleotide sequences in fasta format)
    -m  three align option (1.Tree 2.MIX)
         1. Tree     more accurate but slower
         3. MIX      less accurate but faster

  optional arguments: 
    -o  Output file path (default: infile.mode)
```

### Change Log
---

- 30-11-2021, version 1.0

  fix some bugs in multikband

- 18-10-2021, version 0.3-alpha
  
  add cluster Tree mode, and fix some problem in FM index

- 3-9-2021, version 0.2-alpha
  
  fix clustering problems

- 12-5-2021, version 0.1-alpha
  
  inital version
  

### Dependencies

MACF requires JDK environment (version >= 8.0).

- [Download JDK](https://www.oracle.com/java/technologies/downloads/)

- [Installation Guide](https://docs.oracle.com/en/java/javase/17/install/overview-jdk-installation.html)
